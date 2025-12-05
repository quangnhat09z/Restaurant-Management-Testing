import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import java.util.Arrays

// ========================================
// 1. KHAI BÁO BIẾN ĐẦU VÀO (Variables)
// NOTE: Các biến 'account' và 'password' PHẢI được khai báo trong tab Variables
// ========================================

// Biến testType được tính toán dựa trên dữ liệu đầu vào.
String testType = determineTestType(account, password)

// Log variables nhận được
WebUI.comment('========================================')
WebUI.comment('TEST DATA SUMMARY:')
WebUI.comment('  Account (Email): ' + (account ? account : '(EMPTY)'))
WebUI.comment('  Password: ' + (password ? '******' : '(EMPTY)'))
WebUI.comment('  Calculated Test Type: ' + testType)
WebUI.comment('========================================')

// ========== 2. ĐỊNH NGHĨA TEST OBJECTS ==========
TestObject emailInput = new TestObject('email'); emailInput.addProperty('id', ConditionType.EQUALS, 'email')
TestObject passwordInput = new TestObject('password'); passwordInput.addProperty('id', ConditionType.EQUALS, 'password')
TestObject submitBtn = new TestObject('submit'); submitBtn.addProperty('xpath', ConditionType.EQUALS, '//button[@data-testid="btn-submit"]')
// Locator cho các thông báo lỗi chung/server
TestObject errorMsg = new TestObject('error'); errorMsg.addProperty('xpath', ConditionType.EQUALS, '//*[contains(text(), "Login failed") or contains(text(), "error") or contains(text(), "credentials") or @data-testid="error-message"]')

// ========== 3. SETUP BROWSER ==========
WebUI.openBrowser('', FailureHandling.STOP_ON_FAILURE)
WebUI.navigateToUrl('http://localhost:8080/login')
WebUI.maximizeWindow()
WebUI.waitForPageLoad(10)

// Lưu URL gốc
String loginUrl = WebUI.getUrl()

// ========== 4. ĐIỀU PHỐI VÀ THỰC THI (DISPATCHER & EXECUTION) ==========
WebUI.comment('========== RUNNING TEST: ' + testType.toUpperCase() + ' ==========')

WebUI.setText(emailInput, account)
WebUI.setText(passwordInput, password)

// Xử lý các trường hợp cần clearText thủ công trước khi click (Empty Email/Password)
if (testType.contains('empty')) {
    if (testType.contains('email')) {
        WebUI.clearText(emailInput)
        WebUI.executeJavaScript("document.getElementById('email').value = '';", [])
    } else if (testType.contains('password')) {
        WebUI.clearText(passwordInput)
        WebUI.executeJavaScript("document.getElementById('password').value = '';", [])
    }
}

WebUI.click(submitBtn)
WebUI.delay(3) // Chờ đủ lâu cho mọi loại phản hồi (validation hoặc server)

// ========== 5. KIỂM CHỨNG THEO LOẠI TEST (LOGIC MỚI) ==========

if (testType == 'valid') {
    // Kịch bản Tích cực (Positive): Phải chuyển trang và KHÔNG có lỗi.
    verifySuccessfulLogin(errorMsg, loginUrl)
} else {
    // Kịch bản Tiêu cực (Negative): PHẢI ở lại trang login.
    verifyBlockedLogin(errorMsg, loginUrl, testType)
}

WebUI.closeBrowser()

// ========================================
// 6. CÁC HÀM KIỂM THỬ PHỤ TRỢ (HELPER FUNCTIONS)
// ========================================

/**
 * Xác định loại test dựa trên dữ liệu đầu vào.
 */
def determineTestType(email, password) {
    if (email == null || email.isEmpty()) return 'empty_email'
    if (password == null || password.isEmpty()) return 'empty_password'
    
    // Kiểm tra định dạng email cơ bản
    if (!email.contains('@') || !email.contains('.')) return 'invalid_email'
    
    // Giả định: Sử dụng 'valid' trong dữ liệu để phân biệt positive test
    if (email.contains('valid') && password.contains('valid')) return 'valid'
    
    // Mọi trường hợp còn lại có định dạng hợp lệ nhưng không phải 'valid' -> Server-side (Wrong Credentials)
    return 'wrong_credentials'
}

/**
 * Kiểm tra đăng nhập thành công (Valid). Mong đợi: KHÔNG có lỗi và URL thay đổi.
 */
def verifySuccessfulLogin(errorMsg, loginUrl) {
    String currentUrl = WebUI.getUrl()
    boolean hasError = WebUI.verifyElementPresent(errorMsg, 2, FailureHandling.OPTIONAL)
    
    if (!hasError && !currentUrl.contains('login')) {
        WebUI.comment('✅ TEST PASSED: Login successful. Redirected to: ' + currentUrl)
    } else {
        String failureReason = hasError ? 'Error displayed: ' + WebUI.getText(errorMsg) : 'Still on login page.'
        WebUI.comment('❌ TEST FAILED: Valid Login failed unexpectedly. ' + failureReason)
        KeywordUtil.markFailed('Valid Login failed unexpectedly.')
    }
}

/**
 * Kiểm tra đăng nhập bị chặn (Negative). Mong đợi: URL KHÔNG thay đổi.
 */
def verifyBlockedLogin(errorMsg, loginUrl, testType) {
    String currentUrl = WebUI.getUrl()
    
    // 1. Kiểm tra điều kiện chính: URL KHÔNG được thay đổi
    if (!currentUrl.contains('login')) {
        WebUI.comment('❌ TEST FAILED: Login accepted despite ' + testType + '. Redirected to: ' + currentUrl)
        KeywordUtil.markFailed('Negative test failed: Redirected unexpectedly.')
        return
    }
    
    // 2. Nếu URL không đổi -> Đã PASS (bị chặn). Kiểm tra có lỗi hiển thị không (Server/Client)
    WebUI.comment('✅ TEST PASSED: Login blocked as expected.')
    
    boolean hasError = WebUI.verifyElementPresent(errorMsg, 2, FailureHandling.OPTIONAL)
    
    if (hasError) {
        WebUI.comment('   Message: Server error/general message displayed: ' + WebUI.getText(errorMsg))
    } else {
        // Trường hợp bị chặn bởi HTML5 validation (thông báo vàng/cam)
        WebUI.comment('   Message: Form submission blocked (Client-side/HTML5 validation).')
    }
    
    // Đánh dấu PASS vì hành vi quan trọng nhất (bị chặn) đã được đáp ứng.
    // HTML5 validation message (vàng) sẽ không gây FAIL Test Case.
}
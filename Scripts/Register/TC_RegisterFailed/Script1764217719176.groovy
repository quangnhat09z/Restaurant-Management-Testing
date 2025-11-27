import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil
import java.util.Arrays

// ========================================
// 1. KHAI BÁO BIẾN ĐẦU VÀO (Variables)
// NOTE: Các biến đầu vào phải được khai báo trong tab Variables.
// ========================================

// Biến testType được tính toán dựa trên dữ liệu đầu vào.
String testType = determineRegisterTestType(fullName, email, password)

// Log variables nhận được
WebUI.comment('========================================')
WebUI.comment('REGISTER TEST DATA SUMMARY:')
WebUI.comment('  Email: ' + (email ?: '(EMPTY)'))
WebUI.comment('  Test Type: ' + testType)
WebUI.comment('========================================')

// ========== 2. ĐỊNH NGHĨA TEST OBJECTS ==========
TestObject fullNameInput = new TestObject('fullName'); fullNameInput.addProperty('id', ConditionType.EQUALS, 'fullName')
TestObject emailInput = new TestObject('email'); emailInput.addProperty('id', ConditionType.EQUALS, 'email')
TestObject contactInput = new TestObject('contactNumber'); contactInput.addProperty('id', ConditionType.EQUALS, 'contactNumber')
TestObject passwordInput = new TestObject('password'); passwordInput.addProperty('id', ConditionType.EQUALS, 'password')
TestObject addressInput = new TestObject('address'); addressInput.addProperty('id', ConditionType.EQUALS, 'address')
TestObject createAccountBtn = new TestObject('createAccount'); createAccountBtn.addProperty('xpath', ConditionType.EQUALS, '//button[contains(text(), "Create account")]')
// Locator cho các thông báo lỗi chung/server
TestObject errorMsg = new TestObject('error'); errorMsg.addProperty('xpath', ConditionType.EQUALS, '//*[contains(text(), "error") or @data-testid="error-message"]')

// ========== 3. SETUP BROWSER ==========
WebUI.openBrowser('', FailureHandling.STOP_ON_FAILURE)
WebUI.navigateToUrl('http://localhost:8080/register')
WebUI.maximizeWindow()
WebUI.waitForPageLoad(10)

String registerUrl = WebUI.getUrl()

// ========== 4. ĐIỀU PHỐI VÀ THỰC THI (DISPATCHER & EXECUTION) ==========
WebUI.comment('========== RUNNING TEST: ' + testType.toUpperCase() + ' ==========')

// Điền dữ liệu
WebUI.setText(fullNameInput, fullName)
WebUI.setText(emailInput, email)
WebUI.setText(contactInput, contactNumber)
WebUI.setText(passwordInput, password)
WebUI.setText(addressInput, address)

// Xử lý Empty Fields (Cần clear text nếu giá trị truyền vào không phải '')
// Dựa trên testType, chúng ta clear trường bị nhắm mục tiêu để kích hoạt HTML5 validation
if (testType.contains('empty')) {
    if (testType.contains('name')) WebUI.clearText(fullNameInput)
    if (testType.contains('email')) WebUI.clearText(emailInput)
    if (testType.contains('contact')) WebUI.clearText(contactInput)
    if (testType.contains('password')) WebUI.clearText(passwordInput)
    if (testType.contains('address')) WebUI.clearText(addressInput)
    WebUI.delay(0.5) // Cho phép clear text hoàn tất
}

WebUI.click(createAccountBtn)
WebUI.delay(3) 

// ========== 5. KIỂM CHỨNG THEO LOẠI TEST ==========

if (testType == 'valid') {
    // Kịch bản Tích cực (Positive): Phải chuyển trang và KHÔNG có lỗi.
    verifySuccessfulRegister(errorMsg, registerUrl)
} else {
    // Kịch bản Tiêu cực (Negative): PHẢI ở lại trang register.
    verifyBlockedRegister(errorMsg, registerUrl, testType)
}

WebUI.closeBrowser()

// ========================================
// 6. CÁC HÀM KIỂM THỬ PHỤ TRỢ (HELPER FUNCTIONS)
// ========================================

/**
 * Xác định loại test dựa trên dữ liệu đầu vào.
 */
def determineRegisterTestType(name, email, password) {
    if (name == null || name.isEmpty()) return 'empty_name'
    if (email == null || email.isEmpty()) return 'empty_email'
    if (password == null || password.isEmpty()) return 'empty_password'
    
    // Kiểm tra định dạng email cơ bản
    if (!email.contains('@') || !email.contains('.')) return 'invalid_email'
    
    // Giả định: Sử dụng 'valid' trong dữ liệu để phân biệt positive test
    if (email.contains('valid') && password.contains('valid')) return 'valid'
    
    // Mọi trường hợp còn lại có định dạng hợp lệ nhưng không phải 'valid' 
    // -> Giả định là dữ liệu đã tồn tại/sai (Server-side validation)
    return 'duplicate_or_invalid_server'
}

/**
 * Kiểm tra đăng ký thành công (Valid). Mong đợi: KHÔNG có lỗi và URL thay đổi (sang /login hoặc /home).
 */
def verifySuccessfulRegister(errorMsg, registerUrl) {
    String currentUrl = WebUI.getUrl()
    boolean hasError = WebUI.verifyElementPresent(errorMsg, 2, FailureHandling.OPTIONAL)
    
    if (!hasError && !currentUrl.contains('register')) {
        WebUI.comment('✅ TEST PASSED: Register successful. Redirected to: ' + currentUrl)
    } else {
        String failureReason = hasError ? 'Error displayed: ' + WebUI.getText(errorMsg) : 'Still on register page.'
        WebUI.comment('❌ TEST FAILED: Valid Register failed unexpectedly. ' + failureReason)
        KeywordUtil.markFailed('Valid Register failed unexpectedly.')
    }
}

/**
 * Kiểm tra đăng ký bị chặn (Negative). Mong đợi: URL KHÔNG thay đổi.
 */
def verifyBlockedRegister(errorMsg, registerUrl, testType) {
    String currentUrl = WebUI.getUrl()
    
    // 1. Kiểm tra điều kiện chính: URL KHÔNG được thay đổi
    if (!currentUrl.contains('register')) {
        WebUI.comment('❌ TEST FAILED: Register accepted despite ' + testType + '. Redirected to: ' + currentUrl)
        KeywordUtil.markFailed('Negative test failed: Redirected unexpectedly.')
        return
    }
    
    // 2. Nếu URL không đổi -> Đã PASS (bị chặn). Kiểm tra có lỗi hiển thị không (Server/Client)
    WebUI.comment('✅ TEST PASSED: Register blocked as expected.')
    
    boolean hasError = WebUI.verifyElementPresent(errorMsg, 2, FailureHandling.OPTIONAL)
    
    if (hasError) {
        WebUI.comment('   Message: Server error/duplicate user message displayed: ' + WebUI.getText(errorMsg))
    } else {
        // Trường hợp bị chặn bởi HTML5 validation (thông báo vàng/cam)
        WebUI.comment('   Message: Form submission blocked (Client-side/HTML5 validation).')
    }
    
    // Đánh dấu PASS vì hành vi quan trọng nhất (bị chặn) đã được đáp ứng.
    // HTML5 validation message sẽ không gây FAIL Test Case.
}
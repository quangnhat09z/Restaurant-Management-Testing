import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.model.FailureHandling as FailureHandling

// Tạo objects
TestObject emailInput = new TestObject('email')

emailInput.addProperty('id', ConditionType.EQUALS, 'email')

TestObject passwordInput = new TestObject('password')

passwordInput.addProperty('id', ConditionType.EQUALS, 'password')

TestObject submitBtn = new TestObject('submit')

submitBtn.addProperty('xpath', ConditionType.EQUALS, '//button[@data-testid="btn-submit"]')

TestObject errorMsg = new TestObject('error')

errorMsg.addProperty('xpath', ConditionType.EQUALS, '//*[@data-testid="error-message"]')

// Run test
WebUI.openBrowser('', FailureHandling.STOP_ON_FAILURE)

WebUI.navigateToUrl('http://localhost:8080/login')

WebUI.waitForPageLoad(10)

String loginUrl = WebUI.getUrl()

WebUI.setText(emailInput, account)

WebUI.setText(passwordInput, password)

WebUI.click(submitBtn)

WebUI.delay(3)

String currentUrl = WebUI.getUrl()

println('URL after login: ' + currentUrl)

// ========== VERIFY - Test sẽ FAIL nếu không đúng ==========
// Verify KHÔNG CÓ error message
WebUI.verifyElementNotPresent(errorMsg, 2, FailureHandling.CONTINUE_ON_FAILURE)

// Verify URL ĐÃ THAY ĐỔI (không còn /login)
WebUI.verifyNotMatch(currentUrl, loginUrl, false, FailureHandling.CONTINUE_ON_FAILURE)

// Verify URL MỚI không chứa "login"
WebUI.verifyNotMatch(currentUrl, '.*login.*', true, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment('✅ All verifications completed')

WebUI.closeBrowser()
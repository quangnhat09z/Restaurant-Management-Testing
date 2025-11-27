import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.Keys

// ========== DEFINE TEST OBJECTS ==========
TestObject emailInput = new TestObject('email')
emailInput.addProperty('id', ConditionType.EQUALS, 'email')

TestObject passwordInput = new TestObject('password')
passwordInput.addProperty('id', ConditionType.EQUALS, 'password')

TestObject submitBtn = new TestObject('submit')
submitBtn.addProperty('xpath', ConditionType.EQUALS, '//button[@data-testid="btn-submit"]')

TestObject errorMsg = new TestObject('error')
errorMsg.addProperty('xpath', ConditionType.EQUALS, '//*[@data-testid="error-message"]')

TestObject pageTitle = new TestObject('pageTitle')
pageTitle.addProperty('xpath', ConditionType.EQUALS, '//h2[text()="Sign In"]')

TestObject emailLabel = new TestObject('emailLabel')
emailLabel.addProperty('xpath', ConditionType.EQUALS, '//label[text()="Email"]')

TestObject passwordLabel = new TestObject('passwordLabel')
passwordLabel.addProperty('xpath', ConditionType.EQUALS, '//label[text()="Password"]')

TestObject registerLink = new TestObject('registerLink')
registerLink.addProperty('xpath', ConditionType.EQUALS, '//a[text()="Register"]')

TestObject dontHaveAccountText = new TestObject('dontHaveAccountText')
dontHaveAccountText.addProperty('xpath', ConditionType.EQUALS, '//*[contains(text(),"Don\'t have an account?")]')

// ========== SETUP ==========
WebUI.comment('========================================')
WebUI.comment('LOGIN PAGE UI TESTING - START')
WebUI.comment('========================================')

WebUI.openBrowser('', FailureHandling.STOP_ON_FAILURE)
WebUI.navigateToUrl('http://localhost:8080/login')
WebUI.maximizeWindow()
WebUI.waitForPageLoad(10)
// TEST SUITE 2: INPUT FIELDS PROPERTIES
// ========================================
WebUI.comment('')
WebUI.comment('========== TEST SUITE 2: INPUT FIELDS PROPERTIES ==========')

// TC07: Verify email placeholder
WebUI.comment('TC07: Verify email input placeholder')
try {
	String emailPlaceholder = WebUI.getAttribute(emailInput, 'placeholder')
	WebUI.comment('Email placeholder: "' + emailPlaceholder + '"')
	WebUI.verifyEqual(emailPlaceholder, 'Enter your email', FailureHandling.STOP_ON_FAILURE)
	WebUI.comment('✅ TC07 PASSED: Email placeholder correct')
} catch (Exception e) {
	WebUI.takeScreenshot('Screenshots/TC07_Failed.png')
	WebUI.comment('❌ TC07 FAILED: Email placeholder incorrect')
	throw e
}

// TC08: Verify password placeholder
WebUI.comment('TC08: Verify password input placeholder')
try {
	String passwordPlaceholder = WebUI.getAttribute(passwordInput, 'placeholder')
	WebUI.comment('Password placeholder: "' + passwordPlaceholder + '"')
	WebUI.verifyEqual(passwordPlaceholder, 'Enter your password', FailureHandling.STOP_ON_FAILURE)
	WebUI.comment('✅ TC08 PASSED: Password placeholder correct')
} catch (Exception e) {
	WebUI.takeScreenshot('Screenshots/TC08_Failed.png')
	WebUI.comment('❌ TC08 FAILED: Password placeholder incorrect')
	throw e
}

// TC09: Verify password field type is "password" (hidden)
WebUI.comment('TC09: Verify password field type is hidden')
try {
	String passwordType = WebUI.getAttribute(passwordInput, 'type')
	WebUI.verifyEqual(passwordType, 'password', FailureHandling.STOP_ON_FAILURE)
	WebUI.comment('✅ TC09 PASSED: Password is hidden (type="password")')
} catch (Exception e) {
	WebUI.takeScreenshot('Screenshots/TC09_Failed.png')
	WebUI.comment('❌ TC09 FAILED: Password not hidden')
	throw e
}

// TC10: Verify email input accepts text
WebUI.comment('TC10: Verify email input accepts text')
try {
	WebUI.clearText(emailInput)
	WebUI.setText(emailInput, 'test@example.com')
	String enteredEmail = WebUI.getAttribute(emailInput, 'value')
	WebUI.verifyEqual(enteredEmail, 'test@example.com', FailureHandling.STOP_ON_FAILURE)
	WebUI.comment('✅ TC10 PASSED: Email input accepts text')
} catch (Exception e) {
	WebUI.takeScreenshot('Screenshots/TC10_Failed.png')
	WebUI.comment('❌ TC10 FAILED: Email input does not accept text')
	throw e
}

// TC11: Verify password input accepts text
WebUI.comment('TC11: Verify password input accepts text')
try {
	WebUI.clearText(passwordInput)
	WebUI.setText(passwordInput, 'testPassword123')
	String enteredPassword = WebUI.getAttribute(passwordInput, 'value')
	WebUI.verifyEqual(enteredPassword, 'testPassword123', FailureHandling.STOP_ON_FAILURE)
	WebUI.comment('✅ TC11 PASSED: Password input accepts text')
} catch (Exception e) {
	WebUI.takeScreenshot('Screenshots/TC11_Failed.png')
	WebUI.comment('❌ TC11 FAILED: Password input does not accept text')
	throw e
}

// TC12: Verify inputs are enabled (not disabled)
WebUI.comment('TC12: Verify input fields are enabled')
try {
	WebUI.verifyElementNotHasAttribute(emailInput, 'disabled', 2, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyElementNotHasAttribute(passwordInput, 'disabled', 2, FailureHandling.STOP_ON_FAILURE)
	WebUI.comment('✅ TC12 PASSED: Input fields are enabled')
} catch (Exception e) {
	WebUI.takeScreenshot('Screenshots/TC12_Failed.png')
	WebUI.comment('❌ TC12 FAILED: Input fields are disabled')
	throw e
}
WebUI.comment('')
WebUI.comment('========================================')
WebUI.comment('LOGIN PAGE UI TESTING - COMPLETED')
WebUI.comment('========================================')
WebUI.comment('All UI test cases executed. Check report for details.')

WebUI.closeBrowser()
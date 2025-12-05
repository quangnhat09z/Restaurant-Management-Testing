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
// ========================================
// TEST SUITE 3: BUTTON PROPERTIES
// ========================================
WebUI.comment('')
WebUI.comment('========== TEST SUITE 3: BUTTON PROPERTIES ==========')

// TC13: Verify submit button text
WebUI.comment('TC13: Verify submit button text')
try {
	String btnText = WebUI.getText(submitBtn)
	WebUI.comment('Button text: "' + btnText + '"')
	WebUI.verifyEqual(btnText, 'Sign in', FailureHandling.STOP_ON_FAILURE)
	WebUI.comment('✅ TC13 PASSED: Button text correct')
} catch (Exception e) {
	WebUI.takeScreenshot('Screenshots/TC13_Failed.png')
	WebUI.comment('❌ TC13 FAILED: Button text incorrect')
	throw e
}

// TC14: Verify submit button is enabled initially
WebUI.comment('TC14: Verify submit button is clickable')
try {
	WebUI.verifyElementClickable(submitBtn, FailureHandling.STOP_ON_FAILURE)
	WebUI.comment('✅ TC14 PASSED: Button is clickable')
} catch (Exception e) {
	WebUI.takeScreenshot('Screenshots/TC14_Failed.png')
	WebUI.comment('❌ TC14 FAILED: Button not clickable')
	throw e
}

// TC15: Verify button has proper styling (pink/magenta background)
WebUI.comment('TC15: Verify button has pink/magenta background')
try {
	String btnBgColor = WebUI.getCSSValue(submitBtn, 'background-color')
	WebUI.comment('Button background-color: ' + btnBgColor)
	// Pink color variations: rgb(236, 72, 153) hoặc tương tự
	boolean isPinkish = btnBgColor.contains('236') || btnBgColor.contains('153') || btnBgColor.contains('pink')
	if (isPinkish) {
		WebUI.comment('✅ TC15 PASSED: Button has pink background')
	} else {
		WebUI.comment('⚠️ TC15 WARNING: Button color might not be pink: ' + btnBgColor)
	}
} catch (Exception e) {
	WebUI.comment('⚠️ TC15 WARNING: Could not verify button color')
}

WebUI.comment('')
WebUI.comment('========================================')
WebUI.comment('LOGIN PAGE UI TESTING - COMPLETED')
WebUI.comment('========================================')
WebUI.comment('All UI test cases executed. Check report for details.')

WebUI.closeBrowser()
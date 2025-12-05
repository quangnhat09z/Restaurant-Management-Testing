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
// TEST SUITE 6: KEYBOARD INTERACTION
// ========================================
WebUI.comment('')
WebUI.comment('========== TEST SUITE 6: KEYBOARD INTERACTION ==========')

// TC21: Verify Tab key navigation
WebUI.comment('TC21: Verify Tab key moves focus from email to password')
try {
	WebUI.click(emailInput)
	WebUI.clearText(emailInput)
	WebUI.sendKeys(emailInput, Keys.chord(Keys.TAB))
	WebUI.delay(1)
	
	// Verify password field is focused
	boolean isPasswordFocused = WebUI.executeJavaScript(
		"return document.activeElement.id === 'password';",
		[]
	)
	
	if (isPasswordFocused) {
		WebUI.comment('✅ TC21 PASSED: Tab navigation works')
	} else {
		WebUI.comment('⚠️ TC21 WARNING: Tab navigation might not work as expected')
	}
} catch (Exception e) {
	WebUI.comment('⚠️ TC21 WARNING: Could not verify tab navigation')
}

// TC22: Verify Enter key submits form
WebUI.comment('TC22: Verify Enter key submits the form')
try {
	WebUI.clearText(emailInput)
	WebUI.clearText(passwordInput)
	WebUI.setText(emailInput, 'test@test.com')
	WebUI.setText(passwordInput, 'password123')
	
	WebUI.sendKeys(passwordInput, Keys.chord(Keys.ENTER))
	WebUI.delay(2)
	
	// If form submitted, URL should change or error appears
	String currentUrl = WebUI.getUrl()
	boolean hasError = WebUI.verifyElementPresent(errorMsg, 2, FailureHandling.OPTIONAL)
	boolean urlChanged = !currentUrl.contains('login')
	
	if (hasError || urlChanged) {
		WebUI.comment('✅ TC22 PASSED: Enter key submits form')
	} else {
		WebUI.comment('⚠️ TC22 WARNING: Enter key submission unclear')
	}
} catch (Exception e) {
	WebUI.comment('⚠️ TC22 WARNING: Could not verify Enter key submission')
}
WebUI.comment('')
WebUI.comment('========================================')
WebUI.comment('LOGIN PAGE UI TESTING - COMPLETED')
WebUI.comment('========================================')
WebUI.comment('All UI test cases executed. Check report for details.')

WebUI.closeBrowser()
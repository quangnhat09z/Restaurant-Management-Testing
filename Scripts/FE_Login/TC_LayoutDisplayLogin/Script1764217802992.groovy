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
// TEST SUITE 1: LAYOUT & DISPLAY
// ========================================
WebUI.comment('')
WebUI.comment('========== TEST SUITE 1: LAYOUT & DISPLAY ==========')

// TC01: Verify page title displays
WebUI.comment('TC01: Verify page title "Sign In" displays')
try {
    WebUI.verifyElementPresent(pageTitle, 5, FailureHandling.STOP_ON_FAILURE)
    WebUI.verifyElementVisible(pageTitle, FailureHandling.STOP_ON_FAILURE)
    WebUI.verifyElementText(pageTitle, 'Sign In', FailureHandling.STOP_ON_FAILURE)
    WebUI.comment('✅ TC01 PASSED: Page title displays correctly')
} catch (Exception e) {
    WebUI.takeScreenshot('Screenshots/TC01_Failed.png')
    WebUI.comment('❌ TC01 FAILED: Page title not correct')
    throw e
}

// TC02: Verify email input displays
WebUI.comment('TC02: Verify email input field displays')
try {
    WebUI.verifyElementPresent(emailInput, 5, FailureHandling.STOP_ON_FAILURE)
    WebUI.verifyElementVisible(emailInput, FailureHandling.STOP_ON_FAILURE)
    WebUI.comment('✅ TC02 PASSED: Email input displays')
} catch (Exception e) {
    WebUI.takeScreenshot('Screenshots/TC02_Failed.png')
    WebUI.comment('❌ TC02 FAILED: Email input not found')
    throw e
}

// TC03: Verify password input displays
WebUI.comment('TC03: Verify password input field displays')
try {
    WebUI.verifyElementPresent(passwordInput, 5, FailureHandling.STOP_ON_FAILURE)
    WebUI.verifyElementVisible(passwordInput, FailureHandling.STOP_ON_FAILURE)
    WebUI.comment('✅ TC03 PASSED: Password input displays')
} catch (Exception e) {
    WebUI.takeScreenshot('Screenshots/TC03_Failed.png')
    WebUI.comment('❌ TC03 FAILED: Password input not found')
    throw e
}

// TC04: Verify submit button displays
WebUI.comment('TC04: Verify submit button displays')
try {
    WebUI.verifyElementPresent(submitBtn, 5, FailureHandling.STOP_ON_FAILURE)
    WebUI.verifyElementVisible(submitBtn, FailureHandling.STOP_ON_FAILURE)
    WebUI.comment('✅ TC04 PASSED: Submit button displays')
} catch (Exception e) {
    WebUI.takeScreenshot('Screenshots/TC04_Failed.png')
    WebUI.comment('❌ TC04 FAILED: Submit button not found')
    throw e
}

// TC05: Verify labels display
WebUI.comment('TC05: Verify Email and Password labels display')
try {
    WebUI.verifyElementPresent(emailLabel, 5, FailureHandling.STOP_ON_FAILURE)
    WebUI.verifyElementText(emailLabel, 'Email', FailureHandling.STOP_ON_FAILURE)
    
    WebUI.verifyElementPresent(passwordLabel, 5, FailureHandling.STOP_ON_FAILURE)
    WebUI.verifyElementText(passwordLabel, 'Password', FailureHandling.STOP_ON_FAILURE)
    
    WebUI.comment('✅ TC05 PASSED: Labels display correctly')
} catch (Exception e) {
    WebUI.takeScreenshot('Screenshots/TC05_Failed.png')
    WebUI.comment('❌ TC05 FAILED: Labels incorrect')
    throw e
}

// TC06: Verify "Don't have an account?" text and Register link
WebUI.comment('TC06: Verify Register link displays')
try {
    WebUI.verifyElementPresent(dontHaveAccountText, 5, FailureHandling.STOP_ON_FAILURE)
    WebUI.verifyElementPresent(registerLink, 5, FailureHandling.STOP_ON_FAILURE)
    WebUI.verifyElementVisible(registerLink, FailureHandling.STOP_ON_FAILURE)
    WebUI.verifyElementClickable(registerLink, FailureHandling.STOP_ON_FAILURE)
    WebUI.comment('✅ TC06 PASSED: Register link displays and clickable')
} catch (Exception e) {
    WebUI.takeScreenshot('Screenshots/TC06_Failed.png')
    WebUI.comment('❌ TC06 FAILED: Register link issue')
    throw e
}
WebUI.comment('')
WebUI.comment('========================================')
WebUI.comment('LOGIN PAGE UI TESTING - COMPLETED')
WebUI.comment('========================================')
WebUI.comment('All UI test cases executed. Check report for details.')

WebUI.closeBrowser()
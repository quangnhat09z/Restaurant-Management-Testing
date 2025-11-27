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
// TEST SUITE 7: RESPONSIVE DESIGN
// ========================================
WebUI.comment('')
WebUI.comment('========== TEST SUITE 7: RESPONSIVE DESIGN ==========')

// TC23: Verify mobile viewport
WebUI.comment('TC23: Verify layout on mobile screen (375x667)')
try {
	WebUI.setViewPortSize(375, 667)
	WebUI.delay(2)
	
	WebUI.verifyElementVisible(pageTitle, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyElementVisible(emailInput, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyElementVisible(passwordInput, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyElementVisible(submitBtn, FailureHandling.STOP_ON_FAILURE)
	
	WebUI.comment('✅ TC23 PASSED: Mobile layout displays correctly')
} catch (Exception e) {
	WebUI.takeScreenshot('Screenshots/TC23_Failed.png')
	WebUI.comment('❌ TC23 FAILED: Mobile layout issue')
	throw e
}

// TC24: Verify tablet viewport
WebUI.comment('TC24: Verify layout on tablet screen (768x1024)')
try {
	WebUI.setViewPortSize(768, 1024)
	WebUI.delay(2)
	
	WebUI.verifyElementVisible(pageTitle, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyElementVisible(emailInput, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyElementVisible(passwordInput, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyElementVisible(submitBtn, FailureHandling.STOP_ON_FAILURE)
	
	WebUI.comment('✅ TC24 PASSED: Tablet layout displays correctly')
} catch (Exception e) {
	WebUI.takeScreenshot('Screenshots/TC24_Failed.png')
	WebUI.comment('❌ TC24 FAILED: Tablet layout issue')
	throw e
}

// TC25: Verify desktop viewport
WebUI.comment('TC25: Verify layout on desktop screen (1920x1080)')
try {
	WebUI.setViewPortSize(1920, 1080)
	WebUI.delay(2)
	
	WebUI.verifyElementVisible(pageTitle, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyElementVisible(emailInput, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyElementVisible(passwordInput, FailureHandling.STOP_ON_FAILURE)
	WebUI.verifyElementVisible(submitBtn, FailureHandling.STOP_ON_FAILURE)
	
	WebUI.comment('✅ TC25 PASSED: Desktop layout displays correctly')
} catch (Exception e) {
	WebUI.takeScreenshot('Screenshots/TC25_Failed.png')
	WebUI.comment('❌ TC25 FAILED: Desktop layout issue')
	throw e
}
WebUI.comment('')
WebUI.comment('========================================')
WebUI.comment('LOGIN PAGE UI TESTING - COMPLETED')
WebUI.comment('========================================')
WebUI.comment('All UI test cases executed. Check report for details.')

WebUI.closeBrowser()
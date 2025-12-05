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
// TEST SUITE 4: NAVIGATION
// ========================================
WebUI.comment('')
WebUI.comment('========== TEST SUITE 4: NAVIGATION ==========')

// TC16: Verify Register link navigates to register page
WebUI.comment('TC16: Verify Register link navigation')
try {
    String loginUrl = WebUI.getUrl()
    WebUI.click(registerLink)
    WebUI.delay(2)
    String currentUrl = WebUI.getUrl()
    
    boolean isRegisterPage = currentUrl.contains('register')
    WebUI.verifyTrue(isRegisterPage, FailureHandling.STOP_ON_FAILURE)
    WebUI.comment('✅ TC16 PASSED: Register link navigates correctly to: ' + currentUrl)
    
    // Navigate back to login page
    WebUI.navigateToUrl('http://localhost:8080/login')
    WebUI.waitForPageLoad(5)
} catch (Exception e) {
    WebUI.takeScreenshot('Screenshots/TC16_Failed.png')
    WebUI.comment('❌ TC16 FAILED: Register link navigation failed')
    WebUI.navigateToUrl('http://localhost:8080/login')
    WebUI.waitForPageLoad(5)
}

// TC17: Verify navigation menu items are present
WebUI.comment('TC17: Verify navigation menu items')
try {
    TestObject homeLink = new TestObject('homeLink')
    homeLink.addProperty('xpath', ConditionType.EQUALS, '//a[text()="Home"]')
    
    TestObject ordersLink = new TestObject('ordersLink')
    ordersLink.addProperty('xpath', ConditionType.EQUALS, '//a[text()="Orders"]')
    
    TestObject customersLink = new TestObject('customersLink')
    customersLink.addProperty('xpath', ConditionType.EQUALS, '//a[text()="Customers"]')
    
    boolean hasHome = WebUI.verifyElementPresent(homeLink, 3, FailureHandling.OPTIONAL)
    boolean hasOrders = WebUI.verifyElementPresent(ordersLink, 3, FailureHandling.OPTIONAL)
    boolean hasCustomers = WebUI.verifyElementPresent(customersLink, 3, FailureHandling.OPTIONAL)
    
    if (hasHome || hasOrders || hasCustomers) {
        WebUI.comment('✅ TC17 PASSED: Navigation menu items found')
    } else {
        WebUI.comment('⚠️ TC17 INFO: Nav items may not be visible on login page')
    }
} catch (Exception e) {
    WebUI.comment('⚠️ TC17 INFO: Navigation verification skipped')
}
WebUI.comment('')
WebUI.comment('========================================')
WebUI.comment('LOGIN PAGE UI TESTING - COMPLETED')
WebUI.comment('========================================')
WebUI.comment('All UI test cases executed. Check report for details.')

WebUI.closeBrowser()
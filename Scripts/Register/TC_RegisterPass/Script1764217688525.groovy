import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.testobject.ConditionType as ConditionType

import com.kms.katalon.core.model.FailureHandling as FailureHandling
// Tạo objects cho form Register

TestObject fullNameInput = new TestObject('fullName')
fullNameInput.addProperty('id', ConditionType.EQUALS, 'fullName')
TestObject emailInput = new TestObject('email')
emailInput.addProperty('id', ConditionType.EQUALS, 'email')
TestObject contactInput = new TestObject('contactNumber')
contactInput.addProperty('id', ConditionType.EQUALS, 'contactNumber')
TestObject passwordInput = new TestObject('password')
passwordInput.addProperty('id', ConditionType.EQUALS, 'password')
TestObject addressInput = new TestObject('address')
addressInput.addProperty('id', ConditionType.EQUALS, 'address')
TestObject createAccountBtn = new TestObject('createAccount')
createAccountBtn.addProperty('xpath', ConditionType.EQUALS, '//button[contains(text(), "Create account")]')
TestObject errorMsg = new TestObject('error')
errorMsg.addProperty('xpath', ConditionType.EQUALS, '//*[@data-testid="error-message"]')
TestObject signInLink = new TestObject('signInLink')
signInLink.addProperty('xpath', ConditionType.EQUALS, '//a[contains(text(), "Sign in")]')
WebUI.openBrowser('', FailureHandling.STOP_ON_FAILURE)
WebUI.navigateToUrl('http://localhost:8080/register')
WebUI.waitForPageLoad(10)
String registerUrl = WebUI.getUrl()



println('Register URL: ' + registerUrl)



// Nhập thông tin đăng ký

WebUI.setText(fullNameInput, fullName)



WebUI.setText(emailInput, email)



WebUI.setText(contactInput, contactNumber)



WebUI.setText(passwordInput, password)



WebUI.setText(addressInput, address)



// Click nút Create account

WebUI.click(createAccountBtn)



WebUI.delay(3)



String currentUrl = WebUI.getUrl()



println('URL after register: ' + currentUrl)



// ========== VERIFY - Test sẽ FAIL nếu không đúng ==========

// Verify KHÔNG CÓ error message

WebUI.verifyElementNotPresent(errorMsg, 2, FailureHandling.CONTINUE_ON_FAILURE)



// Verify URL ĐÃ THAY ĐỔI (không còn /register)

WebUI.verifyNotMatch(currentUrl, registerUrl, false, FailureHandling.CONTINUE_ON_FAILURE)



// Verify URL MỚI không chứa "register" (đã chuyển sang login hoặc home)

WebUI.verifyNotMatch(currentUrl, '.*register.*', true, FailureHandling.CONTINUE_ON_FAILURE)



// Verify đã chuyển sang trang login hoặc home

if (currentUrl.contains('/login') || currentUrl.contains('/')) {

    WebUI.comment('✅ Register successful - Redirected to: ' + currentUrl)

} else {

    WebUI.comment('⚠️ Unexpected redirect URL: ' + currentUrl)

}



WebUI.comment('✅ All verifications completed')



WebUI.closeBrowser()
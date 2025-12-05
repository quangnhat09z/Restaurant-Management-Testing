import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// LOGIN
WebUI.openBrowser('')
WebUI.navigateToUrl('http://localhost:8080/login')

TestObject email = new TestObject('email')
email.addProperty('id', ConditionType.EQUALS, 'email')

TestObject pass = new TestObject('pass')
pass.addProperty('id', ConditionType.EQUALS, 'password')

TestObject loginBtn = new TestObject('loginBtn')
loginBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@type='submit']")

WebUI.setText(email, 'thang@gmail.com')
WebUI.setText(pass, '123456')
WebUI.click(loginBtn)
WebUI.waitForPageLoad(10)
WebUI.delay(1)

WebUI.navigateToUrl('http://localhost:8080/orders')

// Filter Preparing
TestObject prepBtn = new TestObject()
prepBtn.addProperty('xpath', ConditionType.EQUALS, "//button[text()='Preparing']")

WebUI.click(prepBtn)
WebUI.delay(1)

WebUI.closeBrowser()

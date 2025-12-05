import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

WebUI.openBrowser('')
WebUI.navigateToUrl('http://localhost:8080/login')

TestObject email = new TestObject()
email.addProperty('id', ConditionType.EQUALS, 'email')

TestObject pass = new TestObject()
pass.addProperty('id', ConditionType.EQUALS, 'password')

TestObject loginBtn = new TestObject()
loginBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@type='submit']")

WebUI.setText(email, 'thang@gmail.com')
WebUI.setText(pass, '123456')
WebUI.click(loginBtn)
WebUI.waitForPageLoad(10)
WebUI.delay(1)

WebUI.navigateToUrl('http://localhost:8080/orders')

// Nút Logout góc phải
TestObject logoutBtn = new TestObject()
logoutBtn.addProperty('xpath', ConditionType.EQUALS, "//button[text()='Logout']")

WebUI.click(logoutBtn)
WebUI.delay(1)

WebUI.closeBrowser()

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// LOGIN
WebUI.openBrowser('')
WebUI.navigateToUrl('http://localhost:8080/login')

TestObject email = new TestObject('email')
email.addProperty('id', ConditionType.EQUALS, 'email')

TestObject pass = new TestObject('password')
pass.addProperty('id', ConditionType.EQUALS, 'password')

TestObject loginBtn = new TestObject('loginBtn')
loginBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@type='submit']")

WebUI.setText(email, 'thang@gmail.com')
WebUI.setText(pass, '123456')
WebUI.click(loginBtn)
WebUI.waitForPageLoad(10)
WebUI.delay(1)

WebUI.navigateToUrl('http://localhost:8080/orders')
WebUI.waitForPageLoad(10)
WebUI.delay(1)

// Verify 4 Summary Box
TestObject totalBox = new TestObject()
totalBox.addProperty('xpath', ConditionType.EQUALS, "//div[contains(., 'Total')]")

TestObject pendingBox = new TestObject()
pendingBox.addProperty('xpath', ConditionType.EQUALS, "//div[contains(., 'Pending')]")

TestObject preparingBox = new TestObject()
preparingBox.addProperty('xpath', ConditionType.EQUALS, "//div[contains(., 'Preparing')]")

TestObject readyBox = new TestObject()
readyBox.addProperty('xpath', ConditionType.EQUALS, "//div[contains(., 'Ready')]")

WebUI.verifyElementPresent(totalBox, 10)
WebUI.verifyElementPresent(pendingBox, 10)
WebUI.verifyElementPresent(preparingBox, 10)
WebUI.verifyElementPresent(readyBox, 10)

WebUI.closeBrowser()

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// ===== LOGIN =====
WebUI.openBrowser('')
WebUI.navigateToUrl('http://localhost:8080/login')

TestObject emailInput = new TestObject('email')
emailInput.addProperty('id', ConditionType.EQUALS, 'email')

TestObject passwordInput = new TestObject('password')
passwordInput.addProperty('id', ConditionType.EQUALS, 'password')

TestObject loginBtn = new TestObject('loginBtn')
loginBtn.addProperty('xpath', ConditionType.EQUALS, "//button[@type='submit']")

WebUI.setText(emailInput, 'thang@gmail.com')
WebUI.setText(passwordInput, '123456')
WebUI.click(loginBtn)

WebUI.waitForPageLoad(10)
WebUI.delay(1)

// ===== CHUYỂN SANG TRANG ORDERS =====
WebUI.navigateToUrl('http://localhost:8080/orders')
WebUI.waitForPageLoad(10)
WebUI.delay(1)

// ===== VERIFY DASHBOARD =====
TestObject header = new TestObject('header')
header.addProperty('xpath', ConditionType.EQUALS, "//h1[text()='Kitchen Dashboard']")

WebUI.verifyElementPresent(header, 10)

WebUI.closeBrowser()

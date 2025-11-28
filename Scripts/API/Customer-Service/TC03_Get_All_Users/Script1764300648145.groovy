import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper

// ========== TEST: GET ALL USERS ==========
println("="*60)
println("TEST: Get All Users")
println("URL: http://localhost:3003/users") // Cập nhật URL theo project của bạn
println("="*60)

// Send request
ResponseObject response = WS.sendRequest(findTestObject(
	'API/Customer-Service/getAllUsers',
))

// In ra status code
println("Status code: " + response.getStatusCode())

// Verify status code = 200
WS.verifyResponseStatusCode(response, 200)

// Parse response JSON
def jsonResponse = new JsonSlurper().parseText(response.getResponseBodyContent())

// In ra tổng số user
println("Total users received: " + jsonResponse.data.size())

// Kiểm tra cấu trúc dữ liệu user đầu tiên
if (jsonResponse.data.size() > 0) {
	def firstUser = jsonResponse.data[0]
	println("First user details:")
	println("UserID: " + firstUser.userID)
	println("UserName: " + firstUser.userName)
	println("Email: " + firstUser.email)
	println("Role: " + firstUser.role)
	println("IsActive: " + firstUser.isActive)
	println("LastLogin: " + firstUser.lastLogin)
}

// Optional: verify success = true
WS.verifyElementPropertyValue(response, 'success', true)
println("="*60)
println("TC03 PASSED")
println("="*60)


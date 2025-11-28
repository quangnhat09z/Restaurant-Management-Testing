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

// ========== TEST: LOGIN USER ==========
println("="*60)
println("TEST: Login User")
println("URL: http://localhost:3003/login")  // chỉnh theo service bạn
println("="*60)

// Send request
ResponseObject response = WS.sendRequest(findTestObject('API/Customer-Service/Login'))

// Verify status code
WS.verifyResponseStatusCode(response, 200)
println("✓ Status Code: 200 OK")

// Verify response time
long responseTime = response.getElapsedTime()
assert responseTime < 3000 : "Response time too slow: ${responseTime}ms"
println("✓ Response Time: ${responseTime}ms")

// Verify content type
def contentType = response.getHeaderFields().get('Content-Type')[0]
assert contentType.contains('application/json') : "Invalid content type: ${contentType}"
println("✓ Content-Type: application/json")

// Parse and verify response body
def jsonResponse = new groovy.json.JsonSlurper().parseText(response.getResponseText())

// Verify top-level structure
assert jsonResponse instanceof Map : "Response không phải object JSON"
assert jsonResponse.success == true : "Login không thành công"
assert jsonResponse.message == "Login successful" : "Message không đúng"
println("✓ Response structure is valid")
println("  - Success: ${jsonResponse.success}")
println("  - Message: ${jsonResponse.message}")

// Verify user object
def user = jsonResponse.user
assert user != null : "User info is missing"
assert user.userID != null : "UserID missing"
assert user.userName != null : "UserName missing"
assert user.email != null : "Email missing"
assert user.role != null : "Role missing"
println("✓ User info is valid")
println("  - UserID: ${user.userID}")
println("  - UserName: ${user.userName}")
println("  - Email: ${user.email}")
println("  - Role: ${user.role}")

// Verify tokens
assert jsonResponse.accessToken != null : "accessToken missing"
assert jsonResponse.refreshToken != null : "refreshToken missing"
assert jsonResponse.expiresIn != null : "expiresIn missing"
println("✓ Tokens are present")
println("  - AccessToken: ${jsonResponse.accessToken}")
println("  - RefreshToken: ${jsonResponse.refreshToken}")
println("  - ExpiresIn: ${jsonResponse.expiresIn}")

println("="*60)
println("TC01 PASSED")
println("="*60)


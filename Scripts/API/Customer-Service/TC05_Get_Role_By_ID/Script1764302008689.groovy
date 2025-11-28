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

// ================= TEST: GET ROLE BY ID =================
println("="*60)
println("TEST: Get Role By ID")
def userID = 1
println("URL: http://localhost:3003/customers/${userID}/role")
println("="*60)

// Send GET request
ResponseObject response = WS.sendRequest(findTestObject('API/Customer-Service/getRoleByID', [('userID'): userID]))

// Check HTTP status
int statusCode = response.getStatusCode()
println("HTTP Status: ${statusCode}")
WS.verifyResponseStatusCode(response, 200)

// Parse response body
def jsonResponse = new JsonSlurper().parseText(response.getResponseBodyContent())

// Verify success field
WS.verifyEqual(jsonResponse.success, true)
println("Success: ${jsonResponse.success}")

// Verify userID and role
WS.verifyEqual(jsonResponse.userID.toString(), userID.toString())
WS.verifyNotEqual(jsonResponse.role, null)

println("User Role Data:")
println("UserID: ${jsonResponse.userID}")
println("Role: ${jsonResponse.role}")

println("="*60)
println("TC05 PASSED")
println("="*60)

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

// ================= TEST: GET USER BY ID =================
println("="*60)
println("TEST: Get User By ID")
def userID = 3
println("URL: http://localhost:3001/users/${userID}")
println("="*60)

// Send GET request
ResponseObject response = WS.sendRequest(findTestObject('API/Customer-Service/getUserByID', [('userID'): userID]))

// Check HTTP status
int statusCode = response.getStatusCode()
println("HTTP Status: ${statusCode}")
WS.verifyResponseStatusCode(response, 200)

// Parse response body
def jsonResponse = new JsonSlurper().parseText(response.getResponseBodyContent())

// Verify success field
WS.verifyEqual(jsonResponse.success, true)
println("Success: ${jsonResponse.success}")

// Verify data fields
def data = jsonResponse.data
WS.verifyEqual(data.userID, userID)
WS.verifyNotEqual(data.userName, null)
WS.verifyNotEqual(data.email, null)
WS.verifyNotEqual(data.contactNumber, null)
WS.verifyNotEqual(data.address, null)
WS.verifyNotEqual(data.role, null)
WS.verifyNotEqual(data.isActive, null)
WS.verifyNotEqual(data.createdAt, null)
WS.verifyNotEqual(data.updatedAt, null)

println("User Data:")
println("ID: ${data.userID}")
println("Name: ${data.userName}")
println("Email: ${data.email}")
println("Contact: ${data.contactNumber}")
println("Address: ${data.address}")
println("Role: ${data.role}")
println("Active: ${data.isActive}")
println("Created At: ${data.createdAt}")
println("Updated At: ${data.updatedAt}")

println("="*60)
println("TC04 PASSED")
println("="*60)

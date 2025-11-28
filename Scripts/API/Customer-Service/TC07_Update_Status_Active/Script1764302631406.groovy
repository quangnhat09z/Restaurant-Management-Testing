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

// ========== TEST: UPDATE USER STATUS ACTIVE BY ID ==========
println("="*60)
println("TEST: Update User Status Active By ID")
println("URL: http://localhost:3003/customers/4/status")
println("="*60)

// Gửi PUT request
ResponseObject response = WS.sendRequest(findTestObject(
	'API/Customer-Service/updateStatusActive',
	[
		('userID') : '4',
		('isActive') : true  
	]
))

// Parse response
def jsonResponse = new JsonSlurper().parseText(response.getResponseBodyContent())

// In kết quả
println("Response Status Code: " + response.getStatusCode())
println("Response Body: " + jsonResponse)

// Kiểm tra success
assert jsonResponse.success == true
assert jsonResponse.message == 'User status updated successfully'
assert jsonResponse.isActive == true


println("="*60)
println("TC07 PASSED")
println("="*60)

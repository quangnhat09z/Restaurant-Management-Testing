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

WebUI.openBrowser('')

WebUI.navigateToUrl('http://localhost:8080/login')

WebUI.setText(findTestObject('Object Repository/Frontend/Home/TC12/Page_Vite  React/input_Email_email'), '1@test.com')

WebUI.setEncryptedText(findTestObject('Object Repository/Frontend/Home/TC12/Page_Vite  React/input_Password_password'), 'aeHFOx8jV/A=')

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC12/Page_Vite  React/button_Sign in'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Frontend/Home/TC12/Page_Vite  React/img_Logout_w-full h-40 object-cover rounded-t-lg'))

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC12/Page_Vite  React/h3_Classic Margherita Pizza'), 
    'Classic Margherita Pizza')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC12/Page_Vite  React/p_200.788'), '200.788đ')

WebUI.closeBrowser()


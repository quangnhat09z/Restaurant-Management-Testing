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

WebUI.setText(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/input_Email_email'), '1@test.com')

WebUI.setEncryptedText(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/input_Password_password'), 
    'aeHFOx8jV/A=')

WebUI.sendKeys(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/input_Password_password'), Keys.chord(
        Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/button_Add to Cart'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/button_Add to Cart_1'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/button_Add to Cart_2'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/button_Add to Cart_3'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/button_Add to Cart_4'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/button_Add to Cart_5'))

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/td_200788'), '200788đ')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/td_153362'), '153362đ')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/td_156791'), '156791đ')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/td_61711'), '61711đ')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/td_249073'), '249073đ')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/td_203788'), '203788đ')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC16/Page_Vite  React/h3_Total 1025513'), 'Total: 1025513đ')

WebUI.closeBrowser()


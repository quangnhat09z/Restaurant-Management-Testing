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

WebUI.setText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/input_Email_email'), '1@test.com')

WebUI.setEncryptedText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/input_Password_password'), 
    'aeHFOx8jV/A=')

WebUI.sendKeys(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/input_Password_password'), Keys.chord(
        Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/button_Add to Cart'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/button_Add to Cart_1'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/button_Add to Cart_2'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/button_Add to Cart_3'))

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/td_Classic Margherita Pizza'), 
    'Classic Margherita Pizza')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/td_Vegetarian Stir-Fry'), 
    'Vegetarian Stir-Fry')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/td_Chocolate Chip Cookies'), 
    'Chocolate Chip Cookies')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/td_Chicken Alfredo Pasta'), 
    'Chicken Alfredo Pasta')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/button_Confirm Order'), 'Confirm Order')

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/button_Confirm Order'))

WebUI.setText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/input__w-full px-4 py-2 border rounded-lg f_06f297'), 
    '19')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/button_Place Order'), 'Place Order')

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/button_Place Order'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/li_Orders'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/a_Orders'))

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/p_19'), '#19')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/span_Classic Margherita Pizza x1'), 
    'Classic Margherita Pizza x1')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/span_Vegetarian Stir-Fry x1'), 
    'Vegetarian Stir-Fry x1')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/span_Chocolate Chip Cookies x1'), 
    'Chocolate Chip Cookies x1')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC17/Page_Vite  React/span_Chicken Alfredo Pasta x1'), 
    'Chicken Alfredo Pasta x1')

WebUI.closeBrowser()


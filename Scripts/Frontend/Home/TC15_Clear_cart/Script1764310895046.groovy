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

WebUI.setText(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/input_Email_email'), '1@test.com')

WebUI.setEncryptedText(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/input_Password_password'), 
    'aeHFOx8jV/A=')

WebUI.sendKeys(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/input_Password_password'), Keys.chord(
        Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/button_Add to Cart'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/button_Add to Cart_1'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/button_Add to Cart_2'))

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/button_Add to Cart_3'))

WebUI.rightClick(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/td_Classic Margherita Pizza'))

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/td_Classic Margherita Pizza'), 
    'Classic Margherita Pizza')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/td_Vegetarian Stir-Fry'), 
    'Vegetarian Stir-Fry')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/td_Chocolate Chip Cookies'), 
    'Chocolate Chip Cookies')

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/td_Chicken Alfredo Pasta'), 
    'Chicken Alfredo Pasta')

WebUI.click(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/button_Clear'))

WebUI.verifyElementText(findTestObject('Object Repository/Frontend/Home/TC15/Page_Vite  React/p_No items in cart'), 'No items in cart.')

WebUI.closeBrowser()


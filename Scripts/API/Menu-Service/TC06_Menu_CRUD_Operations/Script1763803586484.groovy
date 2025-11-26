import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject

// 1. TEST GET ALL MENU ITEMS
ResponseObject getMenuResponse = WS.sendRequest(findTestObject('Menu-Service/GET Menu List'))
WS.verifyResponseStatusCode(getMenuResponse, 200)
WS.verifyElementPropertyValue(getMenuResponse, '$', 'Array')

// 2. TEST CREATE MENU ITEM
ResponseObject createResponse = WS.sendRequest(findTestObject('Menu-Service/POST Create Menu Item'))
WS.verifyResponseStatusCode(createResponse, 201)
def newItemId = WS.getElementPropertyValue(createResponse, '$.id')
println("Created menu item with ID: " + newItemId)

// 3. TEST GET MENU ITEM BY ID
WS.setGlobalVariable('itemId', newItemId)
ResponseObject getItemResponse = WS.sendRequest(findTestObject('Menu-Service/GET Menu Item By ID'))
WS.verifyResponseStatusCode(getItemResponse, 200)
WS.verifyElementPropertyValue(getItemResponse, '$.name', 'Phở Bò')
WS.verifyElementPropertyValue(getItemResponse, '$.price', 50000)

// 4. TEST UPDATE MENU ITEM
ResponseObject updateResponse = WS.sendRequest(findTestObject('Menu-Service/PUT Update Menu Item'))
WS.verifyResponseStatusCode(updateResponse, 200)
WS.verifyElementPropertyValue(updateResponse, '$.price', 60000)

// 5. TEST DELETE MENU ITEM
ResponseObject deleteResponse = WS.sendRequest(findTestObject('Menu-Service/DELETE Menu Item'))
WS.verifyResponseStatusCode(deleteResponse, 200)

// 6. VERIFY DELETED
ResponseObject verifyDeleteResponse = WS.sendRequest(findTestObject('Menu-Service/GET Menu Item By ID'))
WS.verifyResponseStatusCode(verifyDeleteResponse, 404)
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

// 1. Tạo khách hàng mới
ResponseObject customerResponse = WS.sendRequest(findTestObject('Customer-Service/POST register'))
WS.verifyResponseStatusCode(customerResponse, 201)
def customerId = WS.getElementPropertyValue(customerResponse, '$.id')

// 2. Tạo món ăn
ResponseObject menuResponse = WS.sendRequest(findTestObject('Menu-Service/POST Create Menu Item'))
def menuItemId = WS.getElementPropertyValue(menuResponse, '$.id')

// 3. Tạo đơn hàng
WS.setGlobalVariable('customerId', customerId)
WS.setGlobalVariable('menuItemId', menuItemId)
ResponseObject orderResponse = WS.sendRequest(findTestObject('Order-Service/POST postOrder'))
WS.verifyResponseStatusCode(orderResponse, 201)
def orderId = WS.getElementPropertyValue(orderResponse, '$.id')
WS.verifyElementPropertyValue(orderResponse, '$.customerId', customerId)

// 4. Cập nhật trạng thái đơn hàng
WS.setGlobalVariable('orderId', orderId)
ResponseObject statusResponse = WS.sendRequest(findTestObject('Order-Service/patch updateStatus'))
WS.verifyResponseStatusCode(statusResponse, 200)
WS.verifyElementPropertyValue(statusResponse, '$.status', 'preparing')

// 5. Lấy chi tiết đơn hàng
ResponseObject orderDetailResponse = WS.sendRequest(findTestObject('Order-Service/GET getOrderByID'))
WS.verifyResponseStatusCode(orderDetailResponse, 200)
WS.verifyElementPropertyValue(orderDetailResponse, '$.id', orderId)
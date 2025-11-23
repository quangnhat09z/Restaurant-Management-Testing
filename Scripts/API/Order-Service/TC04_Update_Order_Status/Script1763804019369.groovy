import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable

// ========== TEST: UPDATE ORDER STATUS ==========
println("="*60)
println("TEST: Update Order Status")
println("="*60)

// -----------------------------
// 1. INPUT
// -----------------------------
def orderId = 17   // lấy từ GlobalVariable nếu có
def newStatus = "delivered"

// Danh sách status hợp lệ
def statusFlow = ['pending', 'preparing', 'ready', 'delivered', 'cancelled']

// Kiểm tra status có hợp lệ trước khi gửi request
assert statusFlow.contains(newStatus) : "Invalid status: ${newStatus}. Allowed values: ${statusFlow}"

// -----------------------------
// 2. SEND REQUEST
// -----------------------------
def response = WS.sendRequest(findTestObject('API/Order-Service/updateStatus', 
    [orderId: orderId, status: newStatus]
))

// -----------------------------
// 3. VERIFY STATUS CODE
// -----------------------------
WS.verifyResponseStatusCode(response, 200)

// -----------------------------
// 4. PARSE RESPONSE
// -----------------------------
def responseText = response.getResponseText()
println("RESPONSE BODY: " + responseText)

def jsonResponse = new groovy.json.JsonSlurper().parseText(response.getResponseText())

// -----------------------------
// 5. ASSERT DATA
// -----------------------------
assert jsonResponse.success == true

def data = jsonResponse.data

// Kiểm tra status trả về nằm trong statusFlow
assert statusFlow.contains(data.OrderStatus) : "Response OrderStatus invalid: ${data.OrderStatus}"

// Kiểm tra orderId và status đúng như gửi
assert data.OrderID == orderId.toString()
assert data.OrderStatus == newStatus

println("✓ Order ${orderId} updated successfully to status: ${data.OrderStatus}")

println("\n" + "="*60)
println("TEST PASSED - All status transitions successful")
println("="*60)
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject

// ========== TEST: GET ALL ORDERS ==========
println("="*60)
println("TEST: Get All Orders")
println("URL: http://localhost:3001/orders")
println("="*60)

// Send request
ResponseObject response = WS.sendRequest(findTestObject('API/Order-Service/getAllOrders'))

// Verify status code
WS.verifyResponseStatusCode(response, 200)
println("✓ Status Code: 200 OK")

// Verify response time
long responseTime = response.getElapsedTime()
assert responseTime < 3000 : "Response time too slow: ${responseTime}ms"
println("✓ Response Time: ${responseTime}ms")

// Verify content type
def contentType = response.getHeaderFields().get('Content-Type')[0]
assert contentType.contains('application/json') : "Invalid content type: ${contentType}"
println("✓ Content-Type: application/json")

// Parse and verify response body
def jsonResponse = new groovy.json.JsonSlurper().parseText(response.getResponseText())

// Verify top level is an object
assert jsonResponse instanceof Map : "Response không phải là object JSON"
println("✓ Response is an object")

// Verify data field is an array
assert jsonResponse.data instanceof List : "Data is not an array"
println("✓ data is an array")

def orders = jsonResponse.data

if (orders.size() > 0) {
    println("✓ Found ${orders.size()} orders")

    // Verify first order structure
    def firstOrder = orders[0]
    assert firstOrder.OrderID != null : "OrderID is missing"
    assert firstOrder.CustomerID != null : "CustomerID is missing"
    assert firstOrder.OrderStatus != null : "OrderStatus is missing"

    println("✓ Order structure is valid")
    println("  - Sample Order ID: ${firstOrder.OrderID}")
    println("  - Status: ${firstOrder.OrderStatus}")
    println("  - Total Price: ${firstOrder.TotalPrice}")
} else {
    println("⚠ No orders found in database")
}


println("="*60)
println("TEST PASSED")
println("="*60)
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable

// ========== TEST: GET ORDER BY ID ==========
println("="*60)
println("TEST: Get Order By ID")
println("="*60)

// Use order ID from previous test or set a default
//if (GlobalVariable.OrderID == null) {
//    GlobalVariable.OrderID = 1
//    println("⚠ Using default Order ID: 1")
//} else {
//    println("Using Order ID from previous test: ${GlobalVariable.OrderID}")
//}

// Send request
def response = WS.sendRequest(findTestObject('API/Order-Service/getOrderByOrderID'))

// Verify status code
WS.verifyResponseStatusCode(response, 200)
println("✓ Status Code: 200 OK")

// Parse response
def jsonResponse = new groovy.json.JsonSlurper().parseText(response.getResponseText())

// Verify order details
//assert jsonResponse.id == GlobalVariable.OrderID : "Order ID mismatch"
//println("✓ Order ID matches: ${jsonResponse.id}")

// Display order details
println("\nOrder Details:")
println("  - ID: ${jsonResponse.id}")
println("  - User ID: ${jsonResponse.UserId}")
println("  - Status: ${jsonResponse.status}")
println("  - Total Amount: ${jsonResponse.totalAmount}đ")
println("  - Table Number: ${jsonResponse.tableNumber}")
println("  - Order Type: ${jsonResponse.orderType}")

if (jsonResponse.items) {
    println("\n  Items:")
    jsonResponse.items.each { item ->
        println("    • ${item.name}")
        println("      Quantity: ${item.quantity}")
        println("      Price: ${item.price}đ")
        if (item.notes) println("      Notes: ${item.notes}")
    }
}

println("\n" + "="*60)
println("TEST PASSED")
println("="*60)
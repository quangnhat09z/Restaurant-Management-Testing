import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import internal.GlobalVariable

// ========== TEST: DELETE ORDER ==========
println("="*60)
println("TEST: Delete Order")
println("="*60)

// -----------------------------
// STEP 1: TẠO ORDER ĐỂ XÓA
// -----------------------------
println("\n[STEP 1] Creating test order for deletion...")
ResponseObject createResponse = WS.sendRequest(findTestObject('API/Order-Service/postOrder'))
def createJson = new groovy.json.JsonSlurper().parseText(createResponse.getResponseText())

assert createJson.success == true : "Failed to create test order"
def testOrderId = createJson.data.OrderID

println("✓ Test order created - ID: ${testOrderId}")

// -----------------------------
// STEP 2: XÁC NHẬN ORDER TỒN TẠI
// -----------------------------
println("\n[STEP 2] Verifying order exists before deletion...")
ResponseObject getBeforeResponse = WS.sendRequest(
    findTestObject('API/Order-Service/getOrderByOrderID', [orderId: testOrderId])
)
WS.verifyResponseStatusCode(getBeforeResponse, 200)
println("✓ Order exists and can be retrieved")

// -----------------------------
// STEP 3: XÓA ORDER
// -----------------------------
println("\n[STEP 3] Deleting order ${testOrderId}...")
ResponseObject deleteResponse = WS.sendRequest(
    findTestObject('API/Order-Service/deleteOrder', [orderId: testOrderId])
)

// Verify status code (có thể là 200 hoặc 204)
def deleteStatusCode = deleteResponse.getStatusCode()
assert deleteStatusCode == 200 || deleteStatusCode == 204 : 
    "Delete failed with status: ${deleteStatusCode}"

println("✓ Status Code: ${deleteStatusCode}")

// Parse response nếu có body
if (deleteStatusCode == 200 && deleteResponse.getResponseText()?.trim()) {
    def deleteJson = new groovy.json.JsonSlurper().parseText(deleteResponse.getResponseText())
    
    if (deleteJson.success != null) {
        assert deleteJson.success == true : "Delete operation returned success=false"
        println("✓ Delete success: ${deleteJson.success}")
    }
    
    if (deleteJson.message != null) {
        println("✓ Message: ${deleteJson.message}")
    }
}

println("✓ Order deleted successfully")

// -----------------------------
// STEP 4: XÁC NHẬN ORDER ĐÃ BỊ XÓA
// -----------------------------
println("\n[STEP 4] Verifying order no longer exists...")
ResponseObject getAfterResponse = WS.sendRequest(
    findTestObject('API/Order-Service/getOrderByOrderID', [orderId: testOrderId])
)

def afterStatusCode = getAfterResponse.getStatusCode()

if (afterStatusCode == 404) {
    println("✓ Order not found (404) - Hard delete confirmed")
} else if (afterStatusCode == 200) {
    // Kiểm tra có phải soft delete không
    def afterJson = new groovy.json.JsonSlurper().parseText(getAfterResponse.getResponseText())
    if (afterJson.data?.IsDeleted == true || afterJson.data?.OrderStatus == 'deleted') {
        println("✓ Order marked as deleted (Soft delete)")
    } else {
        throw new AssertionError("Order still exists and not marked as deleted!")
    }
} else {
    throw new AssertionError("Unexpected status code after deletion: ${afterStatusCode}")
}

// -----------------------------
// NEGATIVE TEST: XÓA LẦN 2
// -----------------------------
println("\n[NEGATIVE TEST] Attempting to delete already deleted order...")
ResponseObject deleteAgainResponse = WS.sendRequest(
    findTestObject('API/Order-Service/deleteOrder', [orderId: testOrderId])
)

def deleteAgainStatus = deleteAgainResponse.getStatusCode()
if (deleteAgainStatus == 404) {
    println("✓ Correctly returned 404 for already deleted order")
} else if (deleteAgainStatus == 400) {
    println("✓ Correctly returned 400 Bad Request")
} else {
    println("⚠ Unexpected status: ${deleteAgainStatus} (should be 404 or 400)")
}

println("\n" + "="*60)
println("TEST PASSED - DELETE ORDER")
println("="*60)
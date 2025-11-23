import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import internal.GlobalVariable
import groovy.json.JsonSlurper

// ========================================================================
//                    COMPLETE ORDER FLOW TEST
// ========================================================================
println("="*70)
println("        RESTAURANT ORDER SERVICE - COMPLETE FLOW TEST")
println("="*70)

def testOrderId = null
def testResults = []

try {
    // ====================================================================
    // STEP 1: LẤY DANH SÁCH ĐƠN HÀNG BAN ĐẦU
    // ====================================================================
    println("\n[STEP 1/8] Getting initial order list...")
    println("-"*70)
    
    ResponseObject getAllBeforeResponse = WS.sendRequest(
        findTestObject('API/Order-Service/getAllOrders')
    )
    
    WS.verifyResponseStatusCode(getAllBeforeResponse, 200)
    
    def getAllBeforeJson = new JsonSlurper().parseText(
        getAllBeforeResponse.getResponseText()
    )
    
    assert getAllBeforeJson instanceof Map : "Response is not JSON object"
    assert getAllBeforeJson.data instanceof List : "data field is not array"
    
    def countBefore = getAllBeforeJson.data.size()
    println("✓ Current order count: ${countBefore}")
    testResults << "[1/8] Get All Orders (Before): PASS"
    
    // ====================================================================
    // STEP 2: TẠO ĐƠN HÀNG MỚI
    // ====================================================================
    println("\n[STEP 2/8] Creating new order...")
    println("-"*70)
    
    // Test data
    def customerID = 999
    def customerName = "TestKatalon"
    def contactNumber = "0000000000"
    def tableNumber = 99
    
    def cart = [
        [
            id      : 8,
            name    : "Miso",
            Quantity: 2,
            price   : 30000
        ]
    ]
    
    println("Request Data:")
    println("- CustomerID: ${customerID}")
    println("- CustomerName: ${customerName}")
    println("- TableNumber: ${tableNumber}")
    println("- Cart: ${cart}")
    
    // Send request
    ResponseObject createResponse = WS.sendRequest(
        findTestObject(
            'API/Order-Service/postOrder',
            [
                'CustomerID'   : customerID,
                'CustomerName' : customerName,
                'ContactNumber': contactNumber,
                'TableNumber'  : tableNumber,
                'Cart'         : cart
            ]
        )
    )
    
    // Verify status code
    def createStatusCode = createResponse.getStatusCode()
    assert createStatusCode == 200 || createStatusCode == 201 : 
        "Create failed with status: ${createStatusCode}"
    
    // Parse response
    def createJson = new JsonSlurper().parseText(createResponse.getResponseText())
    
    assert createJson instanceof Map : "Response is not JSON object"
    assert createJson.success == true : "Order creation returned success=false"
    assert createJson.data != null : "data field is missing"
    
    def orderData = createJson.data
    
    // Verify required fields
    assert orderData.OrderID != null : "OrderID is missing"
    assert orderData.CustomerID.toInteger() == customerID : "CustomerID mismatch"
    assert orderData.CustomerName == customerName : "CustomerName mismatch" //Có lỗi
    assert orderData.OrderStatus != null : "OrderStatus is missing"
    assert orderData.TotalPrice != null : "TotalPrice is missing"
    
    // Save order ID
    testOrderId = orderData.OrderID.toString()
    
    println("✓ Order created successfully")
    println("  - Order ID: ${testOrderId}")
    println("  - Customer ID: ${orderData.CustomerID}")
    println("  - Customer Name: ${orderData.CustomerName}")
    println("  - Initial Status: ${orderData.OrderStatus}")
    println("  - Total Price: ${orderData.TotalPrice}đ")
    
    if (orderData.Items) {
        println("  - Number of Items: ${orderData.Items.size()}")
        orderData.Items.each { item ->
            def itemName = item.name ?: item.ItemName
            println("    • ${itemName} x${item.Quantity} = ${item.price * item.Quantity}đ")
        }
    }
    
    testResults << "[2/8] Create Order: PASS"
    
    // ====================================================================
    // STEP 3: LẤY CHI TIẾT ĐƠN HÀNG VỪA TẠO
    // ====================================================================
    println("\n[STEP 3/8] Retrieving order details by ID...")
    println("-"*70)
    
    ResponseObject getByIdResponse = WS.sendRequest(
        findTestObject('API/Order-Service/getOrderByOrderID', [orderId: testOrderId])
    )
    
    WS.verifyResponseStatusCode(getByIdResponse, 200)
    
    def getByIdJson = new JsonSlurper().parseText(
        getByIdResponse.getResponseText()
    )
    
    // Kiểm tra response structure
    assert getByIdJson != null : "Response is null"
    
    // Nếu API trả về { success, data }
    if (getByIdJson.success != null) {
        assert getByIdJson.success == true : "Get order failed"
        assert getByIdJson.data.OrderID.toString() == testOrderId : "Order ID mismatch"
        println("✓ Retrieved order with ID: ${getByIdJson.data.OrderID}")
        println("  - Status: ${getByIdJson.data.OrderStatus}")
        println("  - Customer: ${getByIdJson.data.CustomerName}")
    } 
    // Nếu API trả về trực tiếp order object
    else if (getByIdJson.OrderID != null) {
        assert getByIdJson.OrderID.toString() == testOrderId : "Order ID mismatch"
        println("✓ Retrieved order with ID: ${getByIdJson.OrderID}")
        println("  - Status: ${getByIdJson.OrderStatus}")
    }
    else {
        throw new AssertionError("Cannot find OrderID in response")
    }
    
    testResults << "[3/8] Get Order By ID: PASS"
    
    // Small delay
    Thread.sleep(500)
    
    // ====================================================================
    // STEP 4: CẬP NHẬT TRẠNG THÁI - PREPARING
    // ====================================================================
    println("\n[STEP 4/8] Updating order status to 'preparing'...")
    println("-"*70)
    
    def newStatus1 = 'preparing'
    
    ResponseObject updateStatus1Response = WS.sendRequest(
        findTestObject('API/Order-Service/updateStatus', 
            [orderId: testOrderId, status: newStatus1])
    )
    
    WS.verifyResponseStatusCode(updateStatus1Response, 200)
    
    def updateStatus1Json = new JsonSlurper().parseText(
        updateStatus1Response.getResponseText()
    )
    
    assert updateStatus1Json.success == true : "Status update returned success=false"
    assert updateStatus1Json.data.OrderStatus == newStatus1 : 
        "Status not updated. Expected: ${newStatus1}, Got: ${updateStatus1Json.data.OrderStatus}"
    
    println("✓ Status updated: pending → ${updateStatus1Json.data.OrderStatus}")
    testResults << "[4/8] Update Status (preparing): PASS"
    
    Thread.sleep(500)
    
    // ====================================================================
    // STEP 5: CẬP NHẬT TRẠNG THÁI - READY
    // ====================================================================
    println("\n[STEP 5/8] Updating order status to 'ready'...")
    println("-"*70)
    
    def newStatus2 = 'ready'
    
    ResponseObject updateStatus2Response = WS.sendRequest(
        findTestObject('API/Order-Service/updateStatus', 
            [orderId: testOrderId, status: newStatus2])
    )
    
    WS.verifyResponseStatusCode(updateStatus2Response, 200)
    
    def updateStatus2Json = new JsonSlurper().parseText(
        updateStatus2Response.getResponseText()
    )
    
    assert updateStatus2Json.success == true
    assert updateStatus2Json.data.OrderStatus == newStatus2
    
    println("✓ Status updated: preparing → ${updateStatus2Json.data.OrderStatus}")
    testResults << "[5/8] Update Status (ready): PASS"
    
    Thread.sleep(500)
    
    // ====================================================================
    // STEP 6: CẬP NHẬT TRẠNG THÁI - DELIVERED
    // ====================================================================
    println("\n[STEP 6/8] Updating order status to 'delivered'...")
    println("-"*70)
    
    def newStatus3 = 'delivered'
    
    ResponseObject updateStatus3Response = WS.sendRequest(
        findTestObject('API/Order-Service/updateStatus', 
            [orderId: testOrderId, status: newStatus3])
    )
    
    WS.verifyResponseStatusCode(updateStatus3Response, 200)
    
    def updateStatus3Json = new JsonSlurper().parseText(
        updateStatus3Response.getResponseText()
    )
    
    assert updateStatus3Json.success == true
    assert updateStatus3Json.data.OrderStatus == newStatus3
    
    println("✓ Status updated: ready → ${updateStatus3Json.data.OrderStatus}")
    println("✓ Order lifecycle completed: pending → preparing → ready → delivered")
    testResults << "[6/8] Update Status (delivered): PASS"
    
    Thread.sleep(500)
    
    // ====================================================================
    // STEP 7: XÁC NHẬN SỐ LƯỢNG ĐƠN HÀNG TĂNG
    // ====================================================================
    println("\n[STEP 7/8] Verifying order count increased...")
    println("-"*70)
    
    ResponseObject getAllAfterResponse = WS.sendRequest(
        findTestObject('API/Order-Service/getAllOrders')
    )
    
    WS.verifyResponseStatusCode(getAllAfterResponse, 200)
    
    def getAllAfterJson = new JsonSlurper().parseText(
        getAllAfterResponse.getResponseText()
    )
    
    def countAfter = getAllAfterJson.data.size()
    
    assert countAfter == countBefore + 1 : 
        "Order count mismatch. Before: ${countBefore}, After: ${countAfter}"
    
    println("✓ Order count verified: ${countBefore} → ${countAfter}")
    
    // Verify test order exists in list
    def foundOrder = getAllAfterJson.data.find { 
        it.OrderID.toString() == testOrderId 
    }
    assert foundOrder != null : "Created order not found in list"
    println("✓ Test order found in order list")
    println("  - Order ID: ${foundOrder.OrderID}")
    println("  - Status: ${foundOrder.OrderStatus}")
    println("  - Customer: ${foundOrder.CustomerName}")
    
    testResults << "[7/8] Verify Order Count: PASS"
    
    // ====================================================================
    // STEP 8: XÓA ĐƠN HÀNG TEST (CLEANUP)
    // ====================================================================
    println("\n[STEP 8/8] Cleaning up - Deleting test order...")
    println("-"*70)
    
    ResponseObject deleteResponse = WS.sendRequest(
        findTestObject('API/Order-Service/deleteOrder', [orderId: testOrderId])
    )
    
    def deleteStatusCode = deleteResponse.getStatusCode()
    assert deleteStatusCode == 200 || deleteStatusCode == 204 : 
        "Delete failed with status: ${deleteStatusCode}"
    
    println("✓ Delete request sent successfully (Status: ${deleteStatusCode})")
    
    // Parse delete response if body exists
    if (deleteStatusCode == 200 && deleteResponse.getResponseText()?.trim()) {
        def deleteJson = new JsonSlurper().parseText(deleteResponse.getResponseText())
        if (deleteJson.success != null) {
            assert deleteJson.success == true : "Delete returned success=false"
            println("✓ Delete success: ${deleteJson.success}")
        }
        if (deleteJson.message != null) {
            println("✓ Message: ${deleteJson.message}")
        }
    }
    
    // Verify deletion
    Thread.sleep(500)
    println("\nVerifying deletion...")
    
    ResponseObject verifyDeleteResponse = WS.sendRequest(
        findTestObject('API/Order-Service/getOrderByOrderID', [orderId: testOrderId])
    )
    
    def verifyStatus = verifyDeleteResponse.getStatusCode()
    
    if (verifyStatus == 404) {
        println("✓ Order not found (404) - Hard delete confirmed")
    } else if (verifyStatus == 200) {
        def verifyJson = new JsonSlurper().parseText(
            verifyDeleteResponse.getResponseText()
        )
        
        // Check for soft delete markers
        if (verifyJson.data?.IsDeleted == true || 
            verifyJson.data?.OrderStatus == 'deleted' ||
            verifyJson.data?.OrderStatus == 'cancelled') {
            println("✓ Order marked as deleted/cancelled (Soft delete)")
        } else {
            println("⚠ WARNING: Order still exists and active!")
            println("  You may need to manually delete Order ID: ${testOrderId}")
        }
    } else {
        println("⚠ Unexpected status after deletion: ${verifyStatus}")
    }
    
    testResults << "[8/8] Delete Order (Cleanup): PASS"
    
    // ====================================================================
    // TEST SUMMARY - SUCCESS
    // ====================================================================
    println("\n" + "="*70)
    println("                    ✓✓✓ ALL TESTS PASSED ✓✓✓")
    println("="*70)
    
    println("\n📊 Test Results Summary:")
    testResults.each { result ->
        println("  ✓ ${result}")
    }
    
    println("\n📈 Order Flow Timeline:")
    println("  1. Created Order ID: ${testOrderId}")
    println("  2. Status: pending → preparing → ready → delivered")
    println("  3. Verified in order list")
    println("  4. Successfully deleted")
    
    println("\n📉 Statistics:")
    println("  - Total Steps: ${testResults.size()}")
    println("  - Passed: ${testResults.size()}")
    println("  - Failed: 0")
    println("  - Success Rate: 100%")
    
    println("\n" + "="*70)
    
} catch (AssertionError e) {
    // ====================================================================
    // TEST FAILED - ASSERTION ERROR
    // ====================================================================
    println("\n" + "="*70)
    println("                    ✗✗✗ TEST FAILED ✗✗✗")
    println("="*70)
    println("❌ Error Type: Assertion Failed")
    println("❌ Error Message: ${e.getMessage()}")
    
    println("\n📊 Completed Steps:")
    testResults.each { result ->
        println("  ✓ ${result}")
    }
    println("  ✗ Failed at: Step ${testResults.size() + 1}/8")
    
    // Emergency Cleanup
    if (testOrderId != null) {
        println("\n" + "-"*70)
        println("🔧 Attempting emergency cleanup...")
        try {
            def cleanupResponse = WS.sendRequest(
                findTestObject('API/Order-Service/deleteOrder', [orderId: testOrderId])
            )
            if (cleanupResponse.getStatusCode() == 200 || cleanupResponse.getStatusCode() == 204) {
                println("✓ Emergency cleanup successful - Order ${testOrderId} deleted")
            }
        } catch (Exception cleanupError) {
            println("⚠ Emergency cleanup failed: ${cleanupError.getMessage()}")
            println("⚠ Please manually delete Order ID: ${testOrderId}")
        }
    }
    
    println("="*70)
    throw e
    
} catch (Exception e) {
    // ====================================================================
    // TEST FAILED - OTHER EXCEPTION
    // ====================================================================
    println("\n" + "="*70)
    println("                    ✗✗✗ TEST FAILED ✗✗✗")
    println("="*70)
    println("❌ Error Type: ${e.getClass().getSimpleName()}")
    println("❌ Error Message: ${e.getMessage()}")
    
    println("\n📚 Stack Trace:")
    e.printStackTrace()
    
    println("\n📊 Completed Steps:")
    testResults.each { result ->
        println("  ✓ ${result}")
    }
    
    // Emergency Cleanup
    if (testOrderId != null) {
        println("\n" + "-"*70)
        println("🔧 Attempting emergency cleanup...")
        try {
            def cleanupResponse = WS.sendRequest(
                findTestObject('API/Order-Service/deleteOrder', [orderId: testOrderId])
            )
            if (cleanupResponse.getStatusCode() == 200 || cleanupResponse.getStatusCode() == 204) {
                println("✓ Emergency cleanup successful")
            }
        } catch (Exception cleanupError) {
            println("⚠ Emergency cleanup failed")
            println("⚠ Please manually delete Order ID: ${testOrderId}")
        }
    }
    
    println("="*70)
    throw e
}
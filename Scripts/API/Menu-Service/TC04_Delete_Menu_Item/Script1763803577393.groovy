import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import internal.GlobalVariable
import groovy.json.JsonSlurper

// ========================================================================
//                    TEST: DELETE MENU ITEM
// ========================================================================
println("="*70)
println("TEST: Delete Menu Item")
println("="*70)

def testItemId = null

try {
    // ====================================================================
    // STEP 1: CREATE TEST ITEM FOR DELETION
    // ====================================================================
    println("\n[STEP 1] Creating test menu item for deletion...")
    println("-"*70)
    
    def testData = [
        name: "Test Item for Deletion",
        ingredients: ["Test ingredient 1", "Test ingredient 2"],
        instructions: ["Test step 1", "Test step 2"],
        prepTimeMinutes: 10,
        cookTimeMinutes: 20,
        servings: 2,
        difficulty: "Easy",
        cuisine: "Test Cuisine",
        caloriesPerServing: 250,
        tags: ["test"],
        userId: 1,
        image: "https://example.com/test.jpg",
        rating: 4.5,
        reviewCount: 10,
        mealType: ["Test"],
        price: 50000
    ]
    
    ResponseObject createResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/postFood', testData)
    )
    
    def createStatusCode = createResponse.getStatusCode()
    assert createStatusCode == 200 || createStatusCode == 201 : 
        "Create failed with status: ${createStatusCode}"
    
    def createJson = new JsonSlurper().parseText(createResponse.getResponseText())
    
    // API có thể trả về data hoặc trực tiếp object
    if (createJson.data != null) {
        testItemId = createJson.data.id
    } else if (createJson.id != null) {
        testItemId = createJson.id
    } else {
        throw new AssertionError("Cannot find ID in create response")
    }
    
    println("✓ Test item created with ID: ${testItemId}")
    
    // ====================================================================
    // STEP 2: VERIFY ITEM EXISTS
    // ====================================================================
    println("\n[STEP 2] Verifying item exists before deletion...")
    println("-"*70)
    
    ResponseObject getBeforeResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/getFoodById', [itemId: testItemId])
    )
    
    WS.verifyResponseStatusCode(getBeforeResponse, 200)
    
    def beforeItem = new JsonSlurper().parseText(getBeforeResponse.getResponseText())
    
    println("✓ Item exists and can be retrieved")
    println("  - ID: ${beforeItem.id}")
    println("  - Name: ${beforeItem.name}")
    println("  - Price: ${beforeItem.price}đ")
    
    // ====================================================================
    // STEP 3: DELETE ITEM
    // ====================================================================
    println("\n[STEP 3] Deleting menu item...")
    println("-"*70)
    
    ResponseObject deleteResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/deleteFoodByID', [itemId: testItemId])
    )
    
    def deleteStatusCode = deleteResponse.getStatusCode()
    assert deleteStatusCode == 200 || deleteStatusCode == 204 : 
        "Delete failed with status: ${deleteStatusCode}"
    
    println("✓ Delete request successful (Status: ${deleteStatusCode})")
    
    // Parse delete response if available
    if (deleteStatusCode == 200 && deleteResponse.getResponseText()?.trim()) {
        def deleteJson = new JsonSlurper().parseText(deleteResponse.getResponseText())
        
        if (deleteJson.message != null) {
            println("✓ Message: ${deleteJson.message}")
        }
        
        if (deleteJson.success != null) {
            assert deleteJson.success == true : "Delete returned success=false"
            println("✓ Success: ${deleteJson.success}")
        }
    }
    
    // ====================================================================
    // STEP 4: VERIFY ITEM DELETED
    // ====================================================================
    println("\n[STEP 4] Verifying item no longer exists...")
    println("-"*70)
    
    Thread.sleep(500)  // Small delay
    
    ResponseObject getAfterResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/getFoodById', [itemId: testItemId])
    )
    
    def afterStatusCode = getAfterResponse.getStatusCode()
    
    if (afterStatusCode == 404) {
        println("✓ Item not found (404) - Hard delete confirmed")
    } else if (afterStatusCode == 200) {
        def afterItem = new JsonSlurper().parseText(getAfterResponse.getResponseText())
        
        // Check for soft delete markers
        if (afterItem.isDeleted == true || afterItem.status == 'deleted') {
            println("✓ Item marked as deleted (Soft delete)")
        } else {
            throw new AssertionError("Item still exists and not marked as deleted!")
        }
    } else {
        throw new AssertionError("Unexpected status after deletion: ${afterStatusCode}")
    }
    
    // ====================================================================
    // STEP 5: NEGATIVE TEST - DELETE AGAIN
    // ====================================================================
    println("\n[STEP 5] Attempting to delete already deleted item...")
    println("-"*70)
    
    ResponseObject deleteAgainResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/deleteFoodByID', [itemId: testItemId])
    )
    
    def deleteAgainStatus = deleteAgainResponse.getStatusCode()
    
    if (deleteAgainStatus == 404) {
        println("✓ Correctly returned 404 for already deleted item")
    } else if (deleteAgainStatus == 400) {
        println("✓ Correctly returned 400 Bad Request")
    } else if (deleteAgainStatus == 200 || deleteAgainStatus == 204) {
        println("⚠ API allows deleting already deleted item (Status: ${deleteAgainStatus})")
    } else {
        println("⚠ Unexpected status: ${deleteAgainStatus}")
    }
    
    // ====================================================================
    // STEP 6: NEGATIVE TEST - DELETE NON-EXISTENT ITEM
    // ====================================================================
    println("\n[STEP 6] Attempting to delete non-existent item...")
    println("-"*70)
    
    def fakeId = 999999
    
    ResponseObject deleteFakeResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/deleteFoodByID', [itemId: fakeId])
    )
    
    def deleteFakeStatus = deleteFakeResponse.getStatusCode()
    
    if (deleteFakeStatus == 404) {
        println("✓ Correctly returned 404 for non-existent item")
    } else if (deleteFakeStatus == 400) {
        println("✓ Correctly returned 400 Bad Request")
    } else {
        println("⚠ Unexpected status for fake ID: ${deleteFakeStatus}")
    }
    
    // ====================================================================
    // SUCCESS
    // ====================================================================
    println("\n" + "="*70)
    println("✓✓✓ TEST PASSED ✓✓✓")
    println("="*70)
    println("Delete Test Summary:")
    println("  - Created test item ID: ${testItemId}")
    println("  - Successfully deleted item")
    println("  - Verified deletion")
    println("  - Tested negative scenarios")
    println("="*70)
    
} catch (AssertionError e) {
    println("\n" + "="*70)
    println("✗✗✗ TEST FAILED ✗✗✗")
    println("="*70)
    println("Error: ${e.getMessage()}")
    
    // Emergency cleanup
    if (testItemId != null) {
        println("\nAttempting emergency cleanup...")
        try {
            WS.sendRequest(
                findTestObject('API/Menu-Service/deleteFoodByID', [itemId: testItemId])
            )
            println("✓ Emergency cleanup successful")
        } catch (Exception cleanupError) {
            println("⚠ Emergency cleanup failed")
            println("⚠ Please manually delete Menu Item ID: ${testItemId}")
        }
    }
    
    println("="*70)
    throw e
    
} catch (Exception e) {
    println("\n" + "="*70)
    println("✗✗✗ TEST FAILED ✗✗✗")
    println("="*70)
    println("Error: ${e.getMessage()}")
    e.printStackTrace()
    
    // Emergency cleanup
    if (testItemId != null) {
        println("\nAttempting emergency cleanup...")
        try {
            WS.sendRequest(
                findTestObject('API/Menu-Service/deleteFoodByID', [itemId: testItemId])
            )
            println("✓ Emergency cleanup successful")
        } catch (Exception cleanupError) {
            println("⚠ Please manually delete Menu Item ID: ${testItemId}")
        }
    }
    
    println("="*70)
    throw e
}
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import internal.GlobalVariable
import groovy.json.JsonSlurper

// ========================================================================
//                    COMPLETE MENU CRUD OPERATIONS
// ========================================================================
println("="*70)
println("        MENU SERVICE - COMPLETE CRUD OPERATIONS TEST")
println("="*70)

def testItemId = null
def testResults = []

try {
    // ====================================================================
    // STEP 1: GET ALL MENU ITEMS (READ - BEFORE)
    // ====================================================================
    println("\n[STEP 1/7] Getting all menu items (before)...")
    println("-"*70)
    
    ResponseObject getAllBeforeResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/getMenu')
    )
    
    WS.verifyResponseStatusCode(getAllBeforeResponse, 200)
    
    def getAllBeforeJson = new JsonSlurper().parseText(
        getAllBeforeResponse.getResponseText()
    )
    
    assert getAllBeforeJson.data instanceof List : "data is not array"
    assert getAllBeforeJson.pagination != null : "pagination is missing"
    
    def countBefore = getAllBeforeJson.pagination.totalItems
    def itemsOnPageBefore = getAllBeforeJson.data.size()
    
    println("✓ Current total items: ${countBefore}")
    println("✓ Items on current page: ${itemsOnPageBefore}")
    println("✓ Current page: ${getAllBeforeJson.pagination.currentPage}/${getAllBeforeJson.pagination.totalPages}")
    
    testResults << "[1/7] Get All Items (Before): PASS"
    
    // ====================================================================
    // STEP 2: CREATE NEW MENU ITEM (CREATE)
    // ====================================================================
    println("\n[STEP 2/7] Creating new menu item...")
    println("-"*70)
    
    def newItem = [
        name: "Phở bò đặc biệt - Test CRUD",
        ingredients: ["bánh phở", "thịt bò", "hành lá", "nước dùng"],
        instructions: ["Đun sôi nước dùng", "Chần bánh phở", "Thêm thịt bò"],
        prepTimeMinutes: 10,
        cookTimeMinutes: 30,
        servings: 2,
        difficulty: "Medium",
        cuisine: "Vietnamese",
        caloriesPerServing: 450,
        tags: ["noodle", "soup", "test"],
        userId: 1,
        image: "https://thanhphuoc.com/pho.jpg",
        rating: 4.8,
        reviewCount: 230,
        mealType: ["Lunch"],
        price: 60000
    ]
    
    println("Creating item: ${newItem.name}")
    println("  - Cuisine: ${newItem.cuisine}")
    println("  - Price: ${newItem.price}đ")
    
    ResponseObject createResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/postFood', newItem)
    )
    
    def createStatusCode = createResponse.getStatusCode()
    assert createStatusCode == 200 || createStatusCode == 201 : 
        "Create failed with status: ${createStatusCode}"
    
    def createJson = new JsonSlurper().parseText(createResponse.getResponseText())
    println(createJson);
    // Parse created item
    def createdItem = null
    if (createJson.data != null) {
        createdItem = createJson.data
    } else if (createJson.id != null) {
        createdItem = createJson
    } else {
        throw new AssertionError("Cannot find created item in response")
    }
    
    assert createdItem.id != null : "Created item ID is missing"
    testItemId = createdItem.id
    
    // Verify created fields
    assert createdItem.name == newItem.name : "Name mismatch"
    assert createdItem.cuisine == newItem.cuisine : "Cuisine mismatch"
    assert createdItem.price == newItem.price : "Price mismatch"
    assert createdItem.rating == newItem.rating : "Rating mismatch"
    
    println("✓ Menu item created successfully")
    println("  - ID: ${testItemId}")
    println("  - Name: ${createdItem.name}")
    println("  - Cuisine: ${createdItem.cuisine}")
    println("  - Price: ${createdItem.price}đ")
    println("  - Rating: ${createdItem.rating}⭐")
    
    testResults << "[2/7] Create Menu Item: PASS"
    
    Thread.sleep(500)
    
    // ====================================================================
    // STEP 3: GET CREATED ITEM BY ID (READ - SINGLE)
    // ====================================================================
    println("\n[STEP 3/7] Retrieving created item by ID...")
    println("-"*70)
    
    ResponseObject getByIdResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/getFoodById', [itemId: testItemId])
    )
    
    WS.verifyResponseStatusCode(getByIdResponse, 200)
    
    def retrievedItem = new JsonSlurper().parseText(getByIdResponse.getResponseText())
    
    // Verify retrieved item matches created item
    assert retrievedItem.id == testItemId : "ID mismatch"
    assert retrievedItem.name == newItem.name : "Name mismatch on retrieval"
    assert retrievedItem.cuisine == newItem.cuisine : "Cuisine mismatch on retrieval"
    assert retrievedItem.price == newItem.price : "Price mismatch on retrieval"
    
    println("✓ Item retrieved successfully")
    println("  - ID: ${retrievedItem.id}")
    println("  - Name: ${retrievedItem.name}")
    println("  - Cuisine: ${retrievedItem.cuisine}")
    println("  - Ingredients: ${retrievedItem.ingredients.size()} items")
    println("  - Instructions: ${retrievedItem.instructions.size()} steps")
    
    testResults << "[3/7] Get Item By ID: PASS"
    
    Thread.sleep(500)
    
    // ====================================================================
    // STEP 4: UPDATE MENU ITEM (UPDATE)
    // ====================================================================
    println("\n[STEP 4/7] Updating menu item...")
    println("-"*70)
    
    def updatedCuisine = "Asian-Vietnamese Fusion"
    def updatedRating = 9.5
    
    println("Updating:")
    println("  - Cuisine: ${retrievedItem.cuisine} → ${updatedCuisine}")
    println("  - Rating: ${retrievedItem.rating} → ${updatedRating}")
    
    ResponseObject updateResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/updateFoodInfo', 
            [itemId: testItemId, cuisine: updatedCuisine, rating: updatedRating])
    )
    
    def updateStatusCode = updateResponse.getStatusCode()
    assert updateStatusCode == 200 || updateStatusCode == 204 : 
        "Update failed with status: ${updateStatusCode}"
    
    println("✓ Update request successful (Status: ${updateStatusCode})")
    
    // Verify update with GET
    Thread.sleep(500)
    
    ResponseObject getAfterUpdateResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/getFoodById', [itemId: testItemId])
    )
    
    def updatedItem = new JsonSlurper().parseText(getAfterUpdateResponse.getResponseText())
    
    assert updatedItem.cuisine == updatedCuisine : 
        "Cuisine not updated. Expected: ${updatedCuisine}, Got: ${updatedItem.cuisine}"
    assert updatedItem.rating == updatedRating : 
        "Rating not updated. Expected: ${updatedRating}, Got: ${updatedItem.rating}"
    
    // Verify other fields unchanged
    assert updatedItem.name == newItem.name : "Name changed unexpectedly"
    assert updatedItem.price == newItem.price : "Price changed unexpectedly"
    
    println("✓ Update verified successfully")
    println("  - Cuisine: ${updatedItem.cuisine} ✓")
    println("  - Rating: ${updatedItem.rating} ✓")
    println("  - Other fields unchanged ✓")
    
    testResults << "[4/7] Update Menu Item: PASS"
    
    // ====================================================================
    // STEP 5: GET ALL MENU ITEMS (READ - AFTER CREATE)
    // ====================================================================
    println("\n[STEP 5/7] Getting all menu items (after create)...")
    println("-"*70)
    
    ResponseObject getAllAfterResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/getMenu')
    )
    
    WS.verifyResponseStatusCode(getAllAfterResponse, 200)
    
    def getAllAfterJson = new JsonSlurper().parseText(
        getAllAfterResponse.getResponseText()
    )
    
    def countAfter = getAllAfterJson.pagination.totalItems
    
    assert countAfter == countBefore + 1 : 
        "Item count mismatch. Expected: ${countBefore + 1}, Got: ${countAfter}"
    
    println("✓ Total items increased: ${countBefore} → ${countAfter}")
    
    // Try to find our test item in the list (might be on another page)
    def foundInList = getAllAfterJson.data.find { it.id == testItemId }
    
    if (foundInList) {
        println("✓ Test item found in current page")
        println("  - Name: ${foundInList.name}")
        println("  - Cuisine: ${foundInList.cuisine}")
    } else {
        println("⚠ Test item not on current page (might be on another page)")
    }
    
    testResults << "[5/7] Get All Items (After): PASS"
    
    Thread.sleep(500)
    
    // ====================================================================
    // STEP 6: DELETE MENU ITEM (DELETE)
    // ====================================================================
    println("\n[STEP 6/7] Deleting menu item...")
    println("-"*70)
    
    println("Deleting item ID: ${testItemId}")
    
    ResponseObject deleteResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/deleteFoodByID', [itemId: testItemId])
    )
    
    def deleteStatusCode = deleteResponse.getStatusCode()
    assert deleteStatusCode == 200 || deleteStatusCode == 204 : 
        "Delete failed with status: ${deleteStatusCode}"
    
    println("✓ Delete request successful (Status: ${deleteStatusCode})")
    
    if (deleteStatusCode == 200 && deleteResponse.getResponseText()?.trim()) {
        def deleteJson = new JsonSlurper().parseText(deleteResponse.getResponseText())
        if (deleteJson.message) {
            println("✓ Message: ${deleteJson.message}")
        }
    }
    
    testResults << "[6/7] Delete Menu Item: PASS"
    
    // ====================================================================
    // STEP 7: VERIFY DELETION
    // ====================================================================
    println("\n[STEP 7/7] Verifying deletion...")
    println("-"*70)
    
    Thread.sleep(500)
    
    ResponseObject verifyDeleteResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/getFoodById', [itemId: testItemId])
    )
    
    def verifyStatus = verifyDeleteResponse.getStatusCode()
    
    if (verifyStatus == 404) {
        println("✓ Item not found (404) - Hard delete confirmed")
    } else if (verifyStatus == 200) {
        def verifyItem = new JsonSlurper().parseText(verifyDeleteResponse.getResponseText())
        
        if (verifyItem.isDeleted == true || verifyItem.status == 'deleted') {
            println("✓ Item marked as deleted (Soft delete)")
        } else {
            println("⚠ WARNING: Item still exists and accessible")
        }
    } else {
        println("⚠ Unexpected status: ${verifyStatus}")
    }
    
    // Verify total count decreased
    ResponseObject getAllFinalResponse = WS.sendRequest(
        findTestObject('API/Menu-Service/getMenu')
    )
    
    def getAllFinalJson = new JsonSlurper().parseText(getAllFinalResponse.getResponseText())
    def countFinal = getAllFinalJson.pagination.totalItems
    
    assert countFinal == countBefore : 
        "Final count mismatch. Expected: ${countBefore}, Got: ${countFinal}"
    
    println("✓ Total items restored: ${countAfter} → ${countFinal}")
    
    testResults << "[7/7] Verify Deletion: PASS"
    
    // ====================================================================
    // SUCCESS SUMMARY
    // ====================================================================
    println("\n" + "="*70)
    println("                    ✓✓✓ ALL TESTS PASSED ✓✓✓")
    println("="*70)
    
    println("\n📊 Test Results Summary:")
    testResults.each { result ->
        println("  ✓ ${result}")
    }
    
    println("\n📈 CRUD Operations Flow:")
    println("  1. READ   - Got all items (${countBefore} items)")
    println("  2. CREATE - Created '${newItem.name}' (ID: ${testItemId})")
    println("  3. READ   - Retrieved created item by ID")
    println("  4. UPDATE - Updated cuisine and rating")
    println("  5. READ   - Verified total count increased (${countBefore} → ${countAfter})")
    println("  6. DELETE - Deleted test item")
    println("  7. READ   - Verified deletion and count restored (${countAfter} → ${countFinal})")
    
    println("\n📉 Statistics:")
    println("  - Total Steps: ${testResults.size()}")
    println("  - Passed: ${testResults.size()}")
    println("  - Failed: 0")
    println("  - Success Rate: 100%")
    println("  - Item Created: ${newItem.name}")
    println("  - Item Price: ${newItem.price}đ")
    println("  - Cuisine: Vietnamese → Asian-Vietnamese Fusion")
    println("  - Final Status: Deleted successfully")
    
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
    println("  ✗ Failed at: Step ${testResults.size() + 1}/7")
    
    // Emergency Cleanup
    if (testItemId != null) {
        println("\n" + "-"*70)
        println("🔧 Attempting emergency cleanup...")
        try {
            def cleanupResponse = WS.sendRequest(
                findTestObject('API/Menu-Service/deleteFoodByID', [itemId: testItemId])
            )
            if (cleanupResponse.getStatusCode() == 200 || cleanupResponse.getStatusCode() == 204) {
                println("✓ Emergency cleanup successful - Item ${testItemId} deleted")
            }
        } catch (Exception cleanupError) {
            println("⚠ Emergency cleanup failed: ${cleanupError.getMessage()}")
            println("⚠ Please manually delete Menu Item ID: ${testItemId}")
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
    if (testItemId != null) {
        println("\n" + "-"*70)
        println("🔧 Attempting emergency cleanup...")
        try {
            def cleanupResponse = WS.sendRequest(
                findTestObject('API/Menu-Service/deleteFoodByID', [itemId: testItemId])
            )
            if (cleanupResponse.getStatusCode() == 200 || cleanupResponse.getStatusCode() == 204) {
                println("✓ Emergency cleanup successful")
            }
        } catch (Exception cleanupError) {
            println("⚠ Emergency cleanup failed")
            println("⚠ Please manually delete Menu Item ID: ${testItemId}")
        }
    }
    
    println("="*70)
    throw e
}
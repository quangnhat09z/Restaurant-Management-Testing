import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import internal.GlobalVariable
import groovy.json.JsonSlurper

// ========================================================================
//                    TEST: UPDATE MENU ITEM
// ========================================================================
println("="*70)
println("TEST: Update Menu Item")
println("="*70)

// -----------------------------
// 1. SETUP TEST DATA
// -----------------------------
println("\n[STEP 1] Setting up test data...")
println("-"*70)

def itemId = 8  // Item to update
def newCuisine = "Italian"
def newRating = 4.9

println("Test Data:")
println("  - Item ID: ${itemId}")
println("  - New Cuisine: ${newCuisine}")
println("  - New Rating: ${newRating}")

// -----------------------------
// 2. GET ORIGINAL DATA (BEFORE UPDATE)
// -----------------------------
println("\n[STEP 2] Getting original item data...")
println("-"*70)

ResponseObject getBeforeResponse = WS.sendRequest(
    findTestObject('API/Menu-Service/getFoodById', [itemId: itemId])
)

WS.verifyResponseStatusCode(getBeforeResponse, 200)

def originalItem = new JsonSlurper().parseText(getBeforeResponse.getResponseText())

println("Original Data:")
println("  - Name: ${originalItem.name}")
println("  - Cuisine: ${originalItem.cuisine}")
println("  - Rating: ${originalItem.rating}")
println("  - Price: ${originalItem.price}đ")

// -----------------------------
// 3. SEND UPDATE REQUEST
// -----------------------------
println("\n[STEP 3] Sending PUT request...")
println("-"*70)

ResponseObject updateResponse = WS.sendRequest(
    findTestObject('API/Menu-Service/updateFoodInfo', 
        [itemId: itemId, cuisine: newCuisine, rating: newRating])
)

// -----------------------------
// 4. VERIFY STATUS CODE
// -----------------------------
println("\n[STEP 4] Verifying status code...")
println("-"*70)

def statusCode = updateResponse.getStatusCode()
assert statusCode == 200 || statusCode == 204 : 
    "Update failed with status: ${statusCode}"

if (statusCode == 200) {
    println("✓ Status Code: 200 OK")
} else {
    println("✓ Status Code: 204 No Content")
}

// -----------------------------
// 5. PARSE UPDATE RESPONSE
// -----------------------------
println("\n[STEP 5] Parsing update response...")
println("-"*70)

def updatedItem = null

if (statusCode == 200 && updateResponse.getResponseText()?.trim()) {
    updatedItem = new JsonSlurper().parseText(updateResponse.getResponseText())
    
    println("Response contains updated item data")
    
    // Verify updated fields
    assert updatedItem.cuisine == newCuisine : 
        "Cuisine not updated. Expected: ${newCuisine}, Got: ${updatedItem.cuisine}"
    assert updatedItem.rating == newRating : 
        "Rating not updated. Expected: ${newRating}, Got: ${updatedItem.rating}"
    
    println("✓ Cuisine updated: ${originalItem.cuisine} → ${updatedItem.cuisine}")
    println("✓ Rating updated: ${originalItem.rating} → ${updatedItem.rating}")
    
} else {
    println("⚠ No content in response body (204), need to verify with GET")
}

// -----------------------------
// 6. VERIFY UPDATE WITH GET REQUEST
// -----------------------------
println("\n[STEP 6] Verifying update with GET request...")
println("-"*70)

Thread.sleep(500)  // Small delay

ResponseObject getAfterResponse = WS.sendRequest(
    findTestObject('API/Menu-Service/getFoodById', [itemId: itemId])
)

WS.verifyResponseStatusCode(getAfterResponse, 200)

def verifyItem = new JsonSlurper().parseText(getAfterResponse.getResponseText())

// Verify updated fields
assert verifyItem.cuisine == newCuisine : 
    "Cuisine verification failed. Expected: ${newCuisine}, Got: ${verifyItem.cuisine}"
assert verifyItem.rating == newRating : 
    "Rating verification failed. Expected: ${newRating}, Got: ${verifyItem.rating}"

println("✓ Verified Cuisine: ${verifyItem.cuisine}")
println("✓ Verified Rating: ${verifyItem.rating}")

// Verify other fields unchanged
assert verifyItem.id == originalItem.id : "ID changed unexpectedly"
assert verifyItem.name == originalItem.name : "Name changed unexpectedly"
assert verifyItem.price == originalItem.price : "Price changed unexpectedly"

println("✓ Other fields remain unchanged")

// -----------------------------
// 7. RESTORE ORIGINAL DATA (OPTIONAL CLEANUP)
// -----------------------------
println("\n[STEP 7] Restoring original data...")
println("-"*70)

ResponseObject restoreResponse = WS.sendRequest(
    findTestObject('API/Menu-Service/updateFoodInfo', 
        [itemId: itemId, cuisine: originalItem.cuisine, rating: originalItem.rating])
)

if (restoreResponse.getStatusCode() == 200 || restoreResponse.getStatusCode() == 204) {
    println("✓ Original data restored successfully")
    println("  - Cuisine: ${newCuisine} → ${originalItem.cuisine}")
    println("  - Rating: ${newRating} → ${originalItem.rating}")
} else {
    println("⚠ Failed to restore original data")
}

// -----------------------------
// 8. SUMMARY
// -----------------------------
println("\n" + "="*70)
println("✓✓✓ TEST PASSED ✓✓✓")
println("="*70)
println("Update Summary:")
println("  - Item ID: ${itemId}")
println("  - Item Name: ${originalItem.name}")
println("  - Cuisine: ${originalItem.cuisine} → ${newCuisine} → ${originalItem.cuisine}")
println("  - Rating: ${originalItem.rating} → ${newRating} → ${originalItem.rating}")
println("  - Status: Update verified and restored")
println("="*70)
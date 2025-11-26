import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import internal.GlobalVariable
import groovy.json.JsonSlurper

// ========================================================================
//                    TEST: GET MENU ITEM BY ID
// ========================================================================
println("="*70)
println("TEST: Get Menu Item By ID")
println("="*70)

// -----------------------------
// 1. SETUP TEST DATA
// -----------------------------
println("\n[STEP 1] Setting up test data...")
println("-"*70)

def itemId = 1  // Test with item ID 1

println("Test Data:")
println("  - Item ID: ${itemId}")

// -----------------------------
// 2. SEND REQUEST
// -----------------------------
println("\n[STEP 2] Sending GET request...")
println("-"*70)

ResponseObject response = WS.sendRequest(
    findTestObject('API/Menu-Service/getFoodById', [itemId: itemId])
)

// -----------------------------
// 3. VERIFY STATUS CODE
// -----------------------------
println("\n[STEP 3] Verifying status code...")
println("-"*70)

WS.verifyResponseStatusCode(response, 200)
println("✓ Status Code: 200 OK")

// -----------------------------
// 4. PARSE RESPONSE
// -----------------------------
println("\n[STEP 4] Parsing response...")
println("-"*70)

def responseText = response.getResponseText()
assert responseText != null && responseText.length() > 0 : "Response body is empty"

def menuItem = new JsonSlurper().parseText(responseText)

// Verify response is object (not wrapped in data field)
assert menuItem instanceof Map : "Response is not JSON object"
println("✓ Response is JSON Object")

// -----------------------------
// 5. VERIFY REQUIRED FIELDS
// -----------------------------
println("\n[STEP 5] Verifying required fields...")
println("-"*70)

assert menuItem.id != null : "id is missing"
assert menuItem.id == itemId : "ID mismatch. Expected: ${itemId}, Got: ${menuItem.id}"
println("✓ ID matches: ${menuItem.id}")

assert menuItem.name != null : "name is missing"
println("✓ name: ${menuItem.name}")

assert menuItem.ingredients != null : "ingredients is missing"
assert menuItem.ingredients instanceof List : "ingredients is not array"
println("✓ ingredients: ${menuItem.ingredients.size()} items")

assert menuItem.instructions != null : "instructions is missing"
assert menuItem.instructions instanceof List : "instructions is not array"
println("✓ instructions: ${menuItem.instructions.size()} steps")

assert menuItem.prepTimeMinutes != null : "prepTimeMinutes is missing"
println("✓ prepTimeMinutes: ${menuItem.prepTimeMinutes}")

assert menuItem.cookTimeMinutes != null : "cookTimeMinutes is missing"
println("✓ cookTimeMinutes: ${menuItem.cookTimeMinutes}")

assert menuItem.servings != null : "servings is missing"
println("✓ servings: ${menuItem.servings}")

assert menuItem.difficulty != null : "difficulty is missing"
println("✓ difficulty: ${menuItem.difficulty}")

assert menuItem.cuisine != null : "cuisine is missing"
println("✓ cuisine: ${menuItem.cuisine}")

assert menuItem.caloriesPerServing != null : "caloriesPerServing is missing"
println("✓ caloriesPerServing: ${menuItem.caloriesPerServing}")

assert menuItem.price != null : "price is missing"
println("✓ price: ${menuItem.price}đ")

// -----------------------------
// 6. DISPLAY DETAILED INFO
// -----------------------------
println("\n[STEP 6] Menu Item Details...")
println("-"*70)

println("📋 ${menuItem.name}")
println("="*70)
println("Basic Information:")
println("  - ID: ${menuItem.id}")
println("  - Cuisine: ${menuItem.cuisine}")
println("  - Difficulty: ${menuItem.difficulty}")
println("  - Price: ${menuItem.price}đ")
println("  - Rating: ${menuItem.rating} (${menuItem.reviewCount} reviews)")
println("  - Calories: ${menuItem.caloriesPerServing} per serving")

println("\nTime & Servings:")
println("  - Prep Time: ${menuItem.prepTimeMinutes} minutes")
println("  - Cook Time: ${menuItem.cookTimeMinutes} minutes")
println("  - Total Time: ${menuItem.prepTimeMinutes + menuItem.cookTimeMinutes} minutes")
println("  - Servings: ${menuItem.servings}")

println("\n🥘 Ingredients (${menuItem.ingredients.size()}):")
menuItem.ingredients.eachWithIndex { ingredient, index ->
    println("  ${index + 1}. ${ingredient}")
}

println("\n📝 Instructions (${menuItem.instructions.size()} steps):")
menuItem.instructions.eachWithIndex { instruction, index ->
    println("  Step ${index + 1}: ${instruction}")
}

if (menuItem.tags) {
    println("\n🏷️  Tags: ${menuItem.tags.join(', ')}")
}

if (menuItem.mealType) {
    println("🍽️  Meal Type: ${menuItem.mealType.join(', ')}")
}

if (menuItem.image) {
    println("\n🖼️  Image: ${menuItem.image}")
}

// -----------------------------
// 7. SAVE FOR LATER TESTS
// -----------------------------
println("\n[STEP 7] Saving item ID for later tests...")
println("-"*70)

//GlobalVariable.MenuItemID = menuItem.id
//println("✓ Saved MenuItemID to GlobalVariable: ${GlobalVariable.MenuItemID}")

// -----------------------------
// 8. SUMMARY
// -----------------------------
println("\n" + "="*70)
println("✓✓✓ TEST PASSED ✓✓✓")
println("="*70)
println("Retrieved: ${menuItem.name}")
println("ID: ${menuItem.id} | Price: ${menuItem.price}đ | Rating: ${menuItem.rating}⭐")
println("="*70)
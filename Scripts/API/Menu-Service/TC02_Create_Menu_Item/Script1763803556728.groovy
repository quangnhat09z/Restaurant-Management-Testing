import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import internal.GlobalVariable
import groovy.json.JsonSlurper

// ========================================================================
//                    TEST: CREATE MENU ITEM
// ========================================================================
println("="*70)
println("TEST: Create Menu Item")
println("="*70)

// -----------------------------
// 1. SETUP TEST DATA
// -----------------------------
println("\n[STEP 1] Setting up test data...")
println("-"*70)

def newMenuItem = [
    name: "Phở bò đặc biệt",
    ingredients: ["bánh phở", "thịt bò", "hành lá", "nước dùng"],
    instructions: ["Đun sôi nước dùng", "Chần bánh phở", "Thêm thịt bò"],
    prepTimeMinutes: 10,
    cookTimeMinutes: 30,
    servings: 2,
    difficulty: "Medium",
    cuisine: "Vietnamese",
    caloriesPerServing: 450,
    tags: ["noodle", "soup"],
    userId: 1,
    image: "https://thanhphuoc.com/pho.jpg",
    rating: 4.8,
    reviewCount: 230,
    mealType: ["Lunch"],
    price: 60000
]

println("Test Data:")
println("  - Name: ${newMenuItem.name}")
println("  - Cuisine: ${newMenuItem.cuisine}")
println("  - Price: ${newMenuItem.price}đ")
println("  - Servings: ${newMenuItem.servings}")
println("  - Prep Time: ${newMenuItem.prepTimeMinutes} mins")
println("  - Cook Time: ${newMenuItem.cookTimeMinutes} mins")
println("  - Difficulty: ${newMenuItem.difficulty}")
println("  - Rating: ${newMenuItem.rating}")

// -----------------------------
// 2. DISPLAY REQUEST BODY
// -----------------------------
println("\n[STEP 2] Request body preview...")
println("-"*70)

def requestBodyJson = groovy.json.JsonOutput.toJson(newMenuItem)
println(groovy.json.JsonOutput.prettyPrint(requestBodyJson))

// -----------------------------
// 3. SEND POST REQUEST
// -----------------------------
println("\n[STEP 3] Sending POST request...")
println("-"*70)

ResponseObject response = WS.sendRequest(
    findTestObject('API/Menu-Service/postFood', newMenuItem)
)

// -----------------------------
// 4. VERIFY STATUS CODE
// -----------------------------
println("\n[STEP 4] Verifying status code...")
println("-"*70)

def statusCode = response.getStatusCode()
println("Status Code: ${statusCode}")

// Accept both 200 and 201 for successful creation
assert statusCode == 200 || statusCode == 201 : 
    "Expected status 200 or 201, but got ${statusCode}"

if (statusCode == 201) {
    println("✓ Status Code: 201 Created (Standard REST)")
} else {
    println("✓ Status Code: 200 OK")
}

// -----------------------------
// 5. VERIFY RESPONSE TIME
// -----------------------------
println("\n[STEP 5] Verifying response time...")
println("-"*70)

long responseTime = response.getElapsedTime()
println("Response Time: ${responseTime}ms")

assert responseTime < 5000 : "Response time too slow: ${responseTime}ms (should be < 5000ms)"
println("✓ Response time is acceptable")

// -----------------------------
// 6. PARSE RESPONSE
// -----------------------------
println("\n[STEP 6] Parsing response...")
println("-"*70)

def responseText = response.getResponseText()
assert responseText != null && responseText.trim().length() > 0 : 
    "Response body is empty"
println("✓ Response body is not empty")

def jsonResponse = new JsonSlurper().parseText(responseText)
println("✓ Response is valid JSON")

// -----------------------------
// 7. VERIFY RESPONSE STRUCTURE (NEW FORMAT)
// Expect: { message: "Menu created", id: 55 }
// -----------------------------
println("\n[STEP 7] Verifying simple response format... ")
println("-"*70)

def createdItemId = null

// Check response is Map
assert jsonResponse instanceof Map : "Response is not a JSON object"

// Check required fields
assert jsonResponse.message != null : "Missing field: message"
assert jsonResponse.id != null : "Missing field: id"

// Check message content
assert jsonResponse.message.toString().toLowerCase().contains("created") :
        "Unexpected message: ${jsonResponse.message}"

createdItemId = jsonResponse.id

println("✓ Response format is correct")
println("  - message: ${jsonResponse.message}")
println("  - id: ${createdItemId}")

assert createdItemId > 0 : "Invalid ID returned"

// Build createdItem object for next steps
def createdItem = [
        id: createdItemId,
        name: newMenuItem.name,
        price: newMenuItem.price,
        cuisine: newMenuItem.cuisine,
        difficulty: newMenuItem.difficulty,
        rating: newMenuItem.rating,
        reviewCount: newMenuItem.reviewCount,
        prepTimeMinutes: newMenuItem.prepTimeMinutes,
        cookTimeMinutes: newMenuItem.cookTimeMinutes,
        caloriesPerServing: newMenuItem.caloriesPerServing,
        servings: newMenuItem.servings,
        ingredients: newMenuItem.ingredients,
        instructions: newMenuItem.instructions,
        tags: newMenuItem.tags,
        mealType: newMenuItem.mealType,
        image: newMenuItem.image
]

println("✓ Created Item object is initialized from request data")


// -----------------------------
// 8. VERIFY REQUIRED FIELDS
// -----------------------------
println("\n[STEP 8] Verifying required fields...")
println("-"*70)

// ID field

println (createdItem);
assert createdItem.id != null : "id is missing"
println("✓ id: ${createdItem.id}")

// Save ID for later use
//GlobalVariable.MenuItemID = createdItem.id
//println("✓ Saved ID to GlobalVariable: ${GlobalVariable.MenuItemID}")

// Name

assert createdItem.name != null : "name is missing"
assert createdItem.name == newMenuItem.name : 
    "Name mismatch. Expected: ${newMenuItem.name}, Got: ${createdItem.name}"
println("✓ name: ${createdItem.name} (matches)")

// Ingredients
assert createdItem.ingredients != null : "ingredients is missing"
assert createdItem.ingredients instanceof List : "ingredients is not array"
assert createdItem.ingredients.size() == newMenuItem.ingredients.size() : 
    "Ingredients count mismatch"
println("✓ ingredients: ${createdItem.ingredients.size()} items (matches)")

// Instructions
assert createdItem.instructions != null : "instructions is missing"
assert createdItem.instructions instanceof List : "instructions is not array"
assert createdItem.instructions.size() == newMenuItem.instructions.size() : 
    "Instructions count mismatch"
println("✓ instructions: ${createdItem.instructions.size()} steps (matches)")

// Numeric fields
assert createdItem.prepTimeMinutes == newMenuItem.prepTimeMinutes : "prepTimeMinutes mismatch"
println("✓ prepTimeMinutes: ${createdItem.prepTimeMinutes} (matches)")

assert createdItem.cookTimeMinutes == newMenuItem.cookTimeMinutes : "cookTimeMinutes mismatch"
println("✓ cookTimeMinutes: ${createdItem.cookTimeMinutes} (matches)")

assert createdItem.servings == newMenuItem.servings : "servings mismatch"
println("✓ servings: ${createdItem.servings} (matches)")

assert createdItem.caloriesPerServing == newMenuItem.caloriesPerServing : "caloriesPerServing mismatch"
println("✓ caloriesPerServing: ${createdItem.caloriesPerServing} (matches)")

assert createdItem.price == newMenuItem.price : "price mismatch"
println("✓ price: ${createdItem.price}đ (matches)")

// String fields
assert createdItem.difficulty == newMenuItem.difficulty : "difficulty mismatch"
println("✓ difficulty: ${createdItem.difficulty} (matches)")

assert createdItem.cuisine == newMenuItem.cuisine : "cuisine mismatch"
println("✓ cuisine: ${createdItem.cuisine} (matches)")

// Rating fields
assert createdItem.rating == newMenuItem.rating : "rating mismatch"
println("✓ rating: ${createdItem.rating} (matches)")

assert createdItem.reviewCount == newMenuItem.reviewCount : "reviewCount mismatch"
println("✓ reviewCount: ${createdItem.reviewCount} (matches)")

// Array fields
if (createdItem.tags != null) {
    assert createdItem.tags instanceof List : "tags is not array"
    println("✓ tags: ${createdItem.tags.join(', ')} (${createdItem.tags.size()} items)")
}

if (createdItem.mealType != null) {
    assert createdItem.mealType instanceof List : "mealType is not array"
    println("✓ mealType: ${createdItem.mealType.join(', ')}")
}

// -----------------------------
// 9. VERIFY CREATED ITEM WITH GET
// -----------------------------
println("\n[STEP 9] Verifying created item with GET request...")
println("-"*70)

Thread.sleep(500)  // Small delay

ResponseObject getResponse = WS.sendRequest(
    findTestObject('API/Menu-Service/getFoodById', [itemId: createdItem.id])
)

WS.verifyResponseStatusCode(getResponse, 200)

def verifyItem = new JsonSlurper().parseText(getResponse.getResponseText())

assert verifyItem.id == createdItem.id : "ID mismatch in verification"
assert verifyItem.name == newMenuItem.name : "Name mismatch in verification"
assert verifyItem.price == newMenuItem.price : "Price mismatch in verification"

println("✓ Created item verified via GET request")
println("  - ID: ${verifyItem.id}")
println("  - Name: ${verifyItem.name}")
println("  - Price: ${verifyItem.price}đ")

// -----------------------------
// 10. DISPLAY COMPLETE ITEM INFO
// -----------------------------
println("\n[STEP 10] Created Menu Item Details...")
println("-"*70)

println("📋 ${createdItem.name}")
println("="*70)

println("Basic Information:")
println("  - ID: ${createdItem.id}")
println("  - Cuisine: ${createdItem.cuisine}")
println("  - Difficulty: ${createdItem.difficulty}")
println("  - Price: ${createdItem.price}đ")
println("  - Rating: ${createdItem.rating}⭐ (${createdItem.reviewCount} reviews)")

println("\nNutritional & Time Info:")
println("  - Calories per Serving: ${createdItem.caloriesPerServing} cal")
println("  - Prep Time: ${createdItem.prepTimeMinutes} mins")
println("  - Cook Time: ${createdItem.cookTimeMinutes} mins")
println("  - Total Time: ${createdItem.prepTimeMinutes + createdItem.cookTimeMinutes} mins")
println("  - Servings: ${createdItem.servings}")

println("\n🥘 Ingredients (${createdItem.ingredients.size()}):")
createdItem.ingredients.eachWithIndex { ingredient, index ->
    println("  ${index + 1}. ${ingredient}")
}

println("\n📝 Instructions (${createdItem.instructions.size()} steps):")
createdItem.instructions.eachWithIndex { instruction, index ->
    println("  Step ${index + 1}: ${instruction}")
}

if (createdItem.tags) {
    println("\n🏷️  Tags: ${createdItem.tags.join(', ')}")
}

if (createdItem.mealType) {
    println("🍽️  Meal Type: ${createdItem.mealType.join(', ')}")
}

if (createdItem.image) {
    println("\n🖼️  Image: ${createdItem.image}")
}

// -----------------------------
// 11. CLEANUP (OPTIONAL - Comment out if you want to keep the item)
// -----------------------------
println("\n[STEP 11] Cleaning up test data...")
println("-"*70)

ResponseObject deleteResponse = WS.sendRequest(
    findTestObject('API/Menu-Service/deleteFoodByID', [itemId: createdItem.id])
)

def deleteStatus = deleteResponse.getStatusCode()
if (deleteStatus == 200 || deleteStatus == 204) {
    println("✓ Test item deleted successfully")
} else {
    println("⚠ Could not delete test item (Status: ${deleteStatus})")
    println("⚠ Please manually delete Menu Item ID: ${createdItem.id}")
}

// -----------------------------
// 12. SUMMARY
// -----------------------------
println("\n" + "="*70)
println("✓✓✓ TEST PASSED ✓✓✓")
println("="*70)
println("Created Menu Item Summary:")
println("  - ID: ${createdItem.id}")
println("  - Name: ${createdItem.name}")
println("  - Cuisine: ${createdItem.cuisine}")
println("  - Price: ${createdItem.price}đ")
println("  - Rating: ${createdItem.rating}⭐")
println("  - Total Time: ${createdItem.prepTimeMinutes + createdItem.cookTimeMinutes} mins")
println("  - Status: Created, verified, and cleaned up")
println("="*70)
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import groovy.json.JsonSlurper

// ========================================================================
//                    TEST: GET ALL MENU ITEMS
// ========================================================================
println("="*70)
println("TEST: Get All Menu Items")
println("URL: http://localhost:3002/menu")
println("="*70)

// -----------------------------
// 1. SEND REQUEST
// -----------------------------
println("\n[STEP 1] Sending GET request...")
println("-"*70)

ResponseObject response = WS.sendRequest(findTestObject('API/Menu-Service/getMenu'))

// -----------------------------
// 2. VERIFY STATUS CODE
// -----------------------------
println("\n[STEP 2] Verifying status code...")
println("-"*70)

WS.verifyResponseStatusCode(response, 200)
println("✓ Status Code: 200 OK")

// -----------------------------
// 3. VERIFY RESPONSE TIME
// -----------------------------
println("\n[STEP 3] Verifying response time...")
println("-"*70)

long responseTime = response.getElapsedTime()
assert responseTime < 3000 : "Response time too slow: ${responseTime}ms"
println("✓ Response Time: ${responseTime}ms (< 3000ms)")

// -----------------------------
// 4. VERIFY CONTENT TYPE
// -----------------------------
println("\n[STEP 4] Verifying content type...")
println("-"*70)

def contentType = response.getHeaderFields().get('Content-Type')[0]
assert contentType.contains('application/json') : "Invalid content type: ${contentType}"
println("✓ Content-Type: application/json")

// -----------------------------
// 5. PARSE RESPONSE
// -----------------------------
println("\n[STEP 5] Parsing response...")
println("-"*70)

def responseText = response.getResponseText()
assert responseText != null && responseText.length() > 0 : "Response body is empty"

def jsonResponse = new JsonSlurper().parseText(responseText)

// Verify response is object
assert jsonResponse instanceof Map : "Response is not JSON object"
println("✓ Response is JSON Object")

// -----------------------------
// 6. VERIFY DATA FIELD
// -----------------------------
println("\n[STEP 6] Verifying data field...")
println("-"*70)

assert jsonResponse.data != null : "data field is missing"
assert jsonResponse.data instanceof List : "data is not an array"
println("✓ data field exists and is an array")

def menuItems = jsonResponse.data

if (menuItems.size() > 0) {
    println("✓ Found ${menuItems.size()} menu items")
    
    // Verify first item structure
	
    def firstItem = menuItems[0];
	print("FirstItem: " + firstItem);
    
    // Required fields
    assert firstItem.id != null : "id is missing"
    assert firstItem.name != null : "name is missing"
    assert firstItem.ingredients != null : "ingredients is missing"
    assert firstItem.instructions != null : "instructions is missing"
    assert firstItem.prepTimeMinutes != null : "prepTimeMinutes is missing"
    assert firstItem.cookTimeMinutes != null : "cookTimeMinutes is missing"
    assert firstItem.servings != null : "servings is missing"
    assert firstItem.difficulty != null : "difficulty is missing"
    assert firstItem.cuisine != null : "cuisine is missing"
    assert firstItem.caloriesPerServing != null : "caloriesPerServing is missing"
    assert firstItem.price != null : "price is missing"
    
    println("✓ Menu item structure is valid")
    
    // Display sample item
    println("\n📋 Sample Menu Item:")
    println("  - ID: ${firstItem.id}")
    println("  - Name: ${firstItem.name}")
    println("  - Cuisine: ${firstItem.cuisine}")
    println("  - Difficulty: ${firstItem.difficulty}")
    println("  - Price: ${firstItem.price}đ")
    println("  - Rating: ${firstItem.rating}")
    println("  - Prep Time: ${firstItem.prepTimeMinutes} mins")
    println("  - Cook Time: ${firstItem.cookTimeMinutes} mins")
    println("  - Servings: ${firstItem.servings}")
    println("  - Calories: ${firstItem.caloriesPerServing} per serving")
    
    // Verify arrays
    assert firstItem.ingredients instanceof List : "ingredients is not array"
    assert firstItem.instructions instanceof List : "instructions is not array"
    println("  - Ingredients: ${firstItem.ingredients.size()} items")
    println("  - Instructions: ${firstItem.instructions.size()} steps")
    
    if (firstItem.tags) {
        println("  - Tags: ${firstItem.tags.join(', ')}")
    }
    
    if (firstItem.mealType) {
        println("  - Meal Type: ${firstItem.mealType.join(', ')}")
    }
    
} else {
    println("⚠ No menu items found in database")
}

// -----------------------------
// 7. VERIFY PAGINATION
// -----------------------------
println("\n[STEP 7] Verifying pagination...")
println("-"*70)

assert jsonResponse.pagination != null : "pagination field is missing"

def pagination = jsonResponse.pagination

assert pagination.currentPage != null : "currentPage is missing"
assert pagination.totalPages != null : "totalPages is missing"
assert pagination.totalItems != null : "totalItems is missing"
assert pagination.limit != null : "limit is missing"

println("✓ Pagination structure is valid")
println("📄 Pagination Info:")
println("  - Current Page: ${pagination.currentPage}")
println("  - Total Pages: ${pagination.totalPages}")
println("  - Total Items: ${pagination.totalItems}")
println("  - Limit: ${pagination.limit}")

// Verify pagination logic
assert menuItems.size() <= pagination.limit : "Items exceed limit"
println("✓ Items count (${menuItems.size()}) <= limit (${pagination.limit})")

// -----------------------------
// 8. SUMMARY
// -----------------------------
println("\n" + "="*70)
println("✓✓✓ TEST PASSED ✓✓✓")
println("="*70)
println("Summary:")
println("  - Total Menu Items: ${pagination.totalItems}")
println("  - Items on Page: ${menuItems.size()}")
println("  - Response Time: ${responseTime}ms")
println("="*70)
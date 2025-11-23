import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.ResponseObject
import internal.GlobalVariable
import groovy.json.JsonSlurper

// ========== TEST: CREATE ORDER ==========
println("=" * 60)
println("TEST: Create New Order")
println("=" * 60)

// -----------------------------
// 1. TEST DATA (REQUEST BODY)
// -----------------------------
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

// -----------------------------
// 2. SEND REQUEST
// -----------------------------
ResponseObject response = WS.sendRequest(
	findTestObject(
		'API/Order-Service/postOrder',
		[
			'CustomerID'   : customerID,
			'CustomerName' : customerName,
			'ContactNumber': contactNumber,
			'TableNumber'  : tableNumber,
			'Cart'          : cart
		]
	)
)

// -----------------------------
// 3. CHECK RESPONSE
// -----------------------------
String responseText = response.getResponseText()
println("\nRESPONSE:")
println(responseText)

assert responseText != null && responseText.length() > 0 : "Response body is empty"

def parsed = new JsonSlurper().parseText(responseText)

// Must be JSON Object
assert parsed instanceof Map : " Response is not a JSON Object"
assert parsed.success == true : " API returned success = false"

println("✓ Success = true")

def orderData = parsed.data
assert orderData != null : " Data field is missing"

// -----------------------------
// 4. ASSERT ORDER INFO
// -----------------------------
assert orderData.OrderID != null : " OrderID is missing"
assert orderData.CustomerID.toInteger() == customerID : " CustomerID mismatch"
assert orderData.CustomerName == customerName : " CustomerName mismatch"
assert orderData.ContactNumber == contactNumber : " ContactNumber mismatch"
assert orderData.TableNumber.toInteger() == tableNumber : " TableNumber mismatch"
assert orderData.OrderStatus != null : " OrderStatus missing"
assert orderData.TotalPrice != null : " TotalPrice missing"

println("✓ Order Info Valid")
println("- OrderID: ${orderData.OrderID}")
println("- Status: ${orderData.OrderStatus}")
println("- Total Price: ${orderData.TotalPrice}")

// -----------------------------
// 5. ASSERT ITEMS
// -----------------------------
assert orderData.Items != null : "Items is missing"
assert orderData.Items.size() == cart.size() : "Items size mismatch"

orderData.Items.eachWithIndex { item, index ->

	def cartItem = cart[index]

	assert item.name == cartItem.name || item.ItemName == cartItem.name
	assert item.Quantity.toInteger() == cartItem.Quantity.toInteger()
	assert item.price.toInteger() == cartItem.price.toInteger()

	println("✓ Item ${index + 1}: ${item.name} x${item.Quantity}")
}

// -----------------------------
// 6. SAVE ORDER ID FOR NEXT TEST
// -----------------------------
//GlobalVariable.orderId = orderData.OrderID.toString();

//println("\n✓ Order created successfully with ID = ${GlobalVariable.orderId}")
println("=" * 60)
println("TEST PASSED")
println("=" * 60)

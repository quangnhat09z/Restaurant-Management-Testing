import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper
import com.kms.katalon.core.webservice.verification.WSResponseManager

// ================= TEST: REGISTER NEW CUSTOMER =================
println("="*60)
println("TEST: Register New Customer")
println("URL: http://localhost:3003/customers/register")
println("="*60)

// --- Gửi request POST ---
def response = WS.sendRequest(findTestObject('API/Customer-Service/Registers'))

// --- In ra response ---
println("Response status code: " + response.getStatusCode())
println("Response body:")
println(response.getResponseBodyContent())

// --- Kiểm tra kết quả ---
def json = new JsonSlurper().parseText(response.getResponseBodyContent())
if (json.success == true) {
    println("REGISTER PASSED: UserID = ${json.userID}")
} else {
    println("REGISTER FAILED: ${json.message}")
}
println("="*60)
println("TC02 PASSED")
println("="*60)

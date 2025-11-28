import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject

// Danh sách API
Map<String, RequestObject> apiList = [
    'Get User By ID'      : findTestObject('API/Customer-Service/getUserByID'),
    'Get Role By ID'      : findTestObject('API/Customer-Service/getRoleByID'),
    'Update User By ID'   : findTestObject('API/Customer-Service/updateUserByID'),
    'Update Status Active': findTestObject('API/Customer-Service/updateStatusActive'),
    'Delete User By ID'   : findTestObject('API/Customer-Service/deleteUser')
]

// List lưu kết quả tổng hợp
List<Map<String, Object>> summaryTable = []

apiList.each { apiName, request ->
    List<Long> responseTimes = []
    
    for (int i = 1; i <= 50; i++) {
        long startTime = System.currentTimeMillis()
        ResponseObject response = WS.sendRequest(request)
        long endTime = System.currentTimeMillis()
        
        long elapsed = endTime - startTime
        responseTimes.add(elapsed)
        
        WS.verifyResponseStatusCode(response, 200)
    }
    
    long total = responseTimes.sum()
    long minTime = responseTimes.min()
    long maxTime = responseTimes.max()
    double avgTime = total / responseTimes.size()
    
    // Lưu vào bảng thống kê
    summaryTable.add([
        'API Name'   : apiName,
        'Requests'   : responseTimes.size(),
        'Avg (ms)'   : avgTime,
        'Min (ms)'   : minTime,
        'Max (ms)'   : maxTime
    ])
}

// In ra bảng thống kê
println("=== Performance Summary Table ===")
println(String.format("%-25s %-10s %-10s %-10s %-10s", "API Name", "Requests", "Avg(ms)", "Min(ms)", "Max(ms)"))
summaryTable.each { row ->
    println(String.format("%-25s %-10d %-10.2f %-10d %-10d",
        row['API Name'], row['Requests'], row['Avg (ms)'], row['Min (ms)'], row['Max (ms)']))
}

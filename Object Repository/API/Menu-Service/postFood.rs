<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>postFood</name>
   <tag></tag>
   <elementGuidId>811649d8-c440-486f-b4f4-0c27c0b80406</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>true</autoUpdateContent>
   <connectionTimeout>-1</connectionTimeout>
   <followRedirects>true</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;{\n  \&quot;name\&quot;: \&quot;Phở bò đặc biệt\&quot;,\n  \&quot;ingredients\&quot;: [\&quot;bánh phở\&quot;, \&quot;thịt bò\&quot;, \&quot;hành lá\&quot;, \&quot;nước dùng\&quot;],\n  \&quot;instructions\&quot;: [\&quot;Đun sôi nước dùng\&quot;, \&quot;Chần bánh phở\&quot;, \&quot;Thêm thịt bò\&quot;],\n  \&quot;prepTimeMinutes\&quot;: 10,\n  \&quot;cookTimeMinutes\&quot;: 30,\n  \&quot;servings\&quot;: 2,\n  \&quot;difficulty\&quot;: \&quot;Medium\&quot;,\n  \&quot;cuisine\&quot;: \&quot;Vietnamese\&quot;,\n  \&quot;caloriesPerServing\&quot;: 450,\n  \&quot;tags\&quot;: [\&quot;noodle\&quot;, \&quot;soup\&quot;],\n  \&quot;userId\&quot;: 1,\n  \&quot;image\&quot;: \&quot;https://thanhphuoc.com/pho.jpg\&quot;,\n  \&quot;rating\&quot;: 4.8,\n  \&quot;reviewCount\&quot;: 230,\n  \&quot;mealType\&quot;: [\&quot;Lunch\&quot;],\n  \&quot;price\&quot;: 60000\n}&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>6eb42e89-2941-4a7c-b69b-92825b2f8651</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>10.4.2</katalonVersion>
   <maxResponseSize>-1</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path></path>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>http://localhost:3002/menu</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>-1</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()

ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>

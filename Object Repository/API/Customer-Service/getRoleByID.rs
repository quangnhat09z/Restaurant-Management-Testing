<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>getRoleByID</name>
   <tag></tag>
   <elementGuidId>34202f82-1387-4df6-bee8-31e3ab4150f1</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <authorizationRequest>
      <authorizationInfo>
         <entry>
            <key>bearerToken</key>
            <value>eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOjEsImVtYWlsIjoidXNlcjFAZXhhbXBsZS5jb20iLCJ1c2VyTmFtZSI6IlVzZXIxIiwicm9sZSI6ImFkbWluIiwiaWF0IjoxNzY0NTQ5MzUyLCJleHAiOjE3NjQ1NTAyNTJ9.70phIHaLQ9pTQ7gwVRQNw9t25MgRy8yWuaUDd1didvo</value>
         </entry>
      </authorizationInfo>
      <authorizationType>Bearer</authorizationType>
   </authorizationRequest>
   <autoUpdateContent>true</autoUpdateContent>
   <connectionTimeout>0</connectionTimeout>
   <followRedirects>true</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent></httpBodyContent>
   <httpBodyType></httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Authorization</name>
      <type>Main</type>
      <value>Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySUQiOjEsImVtYWlsIjoidXNlcjFAZXhhbXBsZS5jb20iLCJ1c2VyTmFtZSI6IlVzZXIxIiwicm9sZSI6ImFkbWluIiwiaWF0IjoxNzY0NTQ5MzUyLCJleHAiOjE3NjQ1NTAyNTJ9.70phIHaLQ9pTQ7gwVRQNw9t25MgRy8yWuaUDd1didvo</value>
      <webElementGuid>e843940d-a0a0-4763-b45d-1028284bee35</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>10.4.2</katalonVersion>
   <maxResponseSize>0</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path></path>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>http://localhost:3003/customers/1/role</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>0</socketTimeout>
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

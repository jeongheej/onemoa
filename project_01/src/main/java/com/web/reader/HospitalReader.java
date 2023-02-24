package com.web.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.config.OpenApiConfig;
import com.web.dto.HospitalDBDTO;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
public class HospitalReader {
	
    @Autowired OpenApiConfig openApiConfig;

	
	public List<HospitalDBDTO> fetchHospitalDtoFromAPI(String numOfRows, String pageNo) throws IOException, InterruptedException{
	   StringBuilder urlBuilder = new StringBuilder(openApiConfig.getUrl()); /*URL*/		 
	   urlBuilder.append("?" + URLEncoder.encode("_type","UTF-8") + "=json"); 
	   urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "="+openApiConfig.getServiceKey()); /*Service Key*/
	   urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지번호*/
	   urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/
	  
	   URL url = new URL(urlBuilder.toString());
	   
       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
       System.out.println("잠깐쉽니다. " );
       Thread.sleep(4000);
       System.out.println("다시 시작 " );
       conn.setRequestMethod("GET");
       conn.setRequestProperty("Content-type", "application/json");
       System.out.println("Response code: " + conn.getResponseCode());
      
      
       BufferedReader rd;
       
       
       if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
           rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
       } else {
           rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
       }
       
       System.out.println("잠깐쉽니다. " );
       Thread.sleep(4000);
       System.out.println("다시 시작 " );
      
       
       
       StringBuilder sb = new StringBuilder();
       String line;
       while ((line = rd.readLine()) != null) {
           sb.append(line);
       }
       rd.close();
       conn.disconnect();
			   
     
       ObjectMapper mapper = new ObjectMapper();
 	   List<HospitalDBDTO> hospitalList = new ArrayList<>();
       JsonNode root = mapper.readTree(sb.toString());
       if(root.path("response").path("body").path("items").has("item")) {
    	   JsonNode item = root.path("response").path("body").path("items").get("item");
    	   if (item.isArray()) {
    		  hospitalList =  new ObjectMapper().readerFor
    		   (new TypeReference<ArrayList<HospitalDBDTO>>() {}).readValue(item);
    	   } else {
    		   hospitalList.add(new ObjectMapper().readerFor
    		   (new TypeReference<HospitalDBDTO>() {}).readValue(item));
    	   }    	  
           
       }
        hospitalList.forEach(System.out::println);
		return hospitalList;   
		   
	}


	
}
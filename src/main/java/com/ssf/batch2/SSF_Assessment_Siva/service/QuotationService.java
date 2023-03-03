package com.ssf.batch2.SSF_Assessment_Siva.service;

import java.io.StringReader;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ssf.batch2.SSF_Assessment_Siva.model.Quotation;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class QuotationService {

    public Quotation getQuotations(List<String> items) throws Exception{

        JsonArrayBuilder jsonArrBuilder = Json.createArrayBuilder();    
        for(String i: items){
            jsonArrBuilder.add(i);
        }    
        JsonArray jsonArr = jsonArrBuilder.build();

        System.out.println(jsonArr.toString());
        
        String url = "https://quotation.chuklee.com/quotation";
       
        RequestEntity<String> req = RequestEntity
            .post(url)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonArr.toString());

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        String payload;
        int statuscode;
        try{
            resp = template.exchange(req, String.class);
            payload = resp.getBody();
            statuscode=resp.getStatusCode().value();
            
        }catch(HttpClientErrorException ex){
            payload = ex.getResponseBodyAsString();
            statuscode = ex.getStatusCode().value();
        }
        
        JsonReader jsonReader = Json.createReader(new StringReader(payload));
        JsonObject jsonObject = jsonReader.readObject();

        System.out.println(jsonObject.toString());

        Quotation quote = new Quotation();
        
        String quoteID = jsonObject.getString("quoteId");
        quote.setQuoteId(quoteID);

        JsonArray jsonQuoteArr = jsonObject.getJsonArray("quotations");

        for(int i = 0; i< jsonQuoteArr.size();i++){
            
            String item = jsonQuoteArr.getJsonObject(i).getString("item");
            float unitPrice = jsonQuoteArr.getJsonObject(i).getJsonNumber("unitPrice").bigDecimalValue().floatValue();

            quote.addQuotation(item, unitPrice);
        }

        return quote;

    }
    
}

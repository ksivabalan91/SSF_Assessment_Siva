package com.ssf.batch2.SSF_Assessment_Siva.service;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ssf.batch2.SSF_Assessment_Siva.model.Items;
import com.ssf.batch2.SSF_Assessment_Siva.model.Quotation;
import com.ssf.batch2.SSF_Assessment_Siva.repository.ItemsRepo;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class ItemsService {
    
    @Autowired
    private ItemsRepo itemsRepo;
    
    public List<Items> getCart(){
        return itemsRepo.getCart();            
    }
    public void addItems(String name, int quantity){
        itemsRepo.addToCart(name, quantity);
    }

    public void printCart(){
        itemsRepo.printCart();
    }

    public Quotation getQuotations(List<String> items) throws Exception{

        JsonArrayBuilder jsonArrBuilder = Json.createArrayBuilder();    
        for(String i: items){
            jsonArrBuilder.add(i);
        }    
        JsonArray jsonArr = jsonArrBuilder.build();
        
        String url = "https://quotation.chuklee.com/quotation";
       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        

        RequestEntity<JsonArray> req = RequestEntity
            .post(url)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonArr);

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


        return null;

    }
    
}

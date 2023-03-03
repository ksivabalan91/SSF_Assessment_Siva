package com.ssf.batch2.SSF_Assessment_Siva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssf.batch2.SSF_Assessment_Siva.model.Items;
import com.ssf.batch2.SSF_Assessment_Siva.repository.ItemsRepo;

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

    
    
}

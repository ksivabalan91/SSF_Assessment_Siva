package com.ssf.batch2.SSF_Assessment_Siva.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssf.batch2.SSF_Assessment_Siva.model.Items;


@Repository
public class ItemsRepo {

    private List<Items> cart = new LinkedList<>();
    
    public void addToCart(String name, int quantity){
        
        System.out.println("repo"+name+quantity);

        if (cart.isEmpty()) {
            cart.add(new Items(name, quantity));
        } else {
            boolean found = false;
            for (int i = 0; i < cart.size(); i++) {
                if (cart.get(i).getName().equals(name)) {
                    int newQuantity = cart.get(i).getQuantity() + quantity;
                    cart.get(i).setQuantity(newQuantity);
                    found = true;
                    break;
                }
            }
            if (!found) {
                cart.add(new Items(name, quantity));
            }
            }
        }

    public List<Items> getCart(){
        return cart;
    }

    public void printCart(){
        System.out.println(cart.toString());
    }
    
}

package com.ssf.batch2.SSF_Assessment_Siva.model;

import jakarta.validation.constraints.Min;

public class Items {

    
    private String Name;
    
    @Min(value=1,message="you must add at least 1 item")
    private int quantity;
    
    public Items() {
    }
    public Items(String name, int quantity) {
        Name = name;
        this.quantity = quantity;
    }

    public String getName() {return Name;    }
    public void setName(String name) {Name = name;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    
    @Override
    public String toString() {
        return "Items [Name=" + Name + ", quantity=" + quantity + "]";
    }
    
    
}

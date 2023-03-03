package com.ssf.batch2.SSF_Assessment_Siva.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssf.batch2.SSF_Assessment_Siva.model.Items;
import com.ssf.batch2.SSF_Assessment_Siva.model.Quotation;
import com.ssf.batch2.SSF_Assessment_Siva.service.ItemsService;
import com.ssf.batch2.SSF_Assessment_Siva.service.QuotationService;

@Controller
@RequestMapping("/")
public class PurchaseOrderController {

@Autowired
private ItemsService itemsSvc;

@Autowired
private QuotationService quotationService;

    @GetMapping("/")
    public String getHome(Model model){       

        List<Items> cart = itemsSvc.getCart();

        if(cart.isEmpty()){
            model.addAttribute("isEmpty", false);
        }else{            
            model.addAttribute("isEmpty", true);
            model.addAttribute("cart", itemsSvc.getCart());
        }
                
        return "view1";
    }

    @PostMapping(path="/cart",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addItem(@RequestBody MultiValueMap<String,String> form ,Model model){

        String name = form.getFirst("item");
        int quantity = Integer.parseInt(form.getFirst("quantity")) ;
        
        System.out.println("controller"+name+quantity);

        itemsSvc.addItems(name, quantity);
        List<Items> cart = itemsSvc.getCart();

        itemsSvc.printCart();

        if(cart.isEmpty()){
            model.addAttribute("isEmpty", false);
        }else{            
            model.addAttribute("isEmpty", true);
            model.addAttribute("cart", itemsSvc.getCart());
        }       

        return "view1";
    }

    @GetMapping(path="/shippingaddress")
    public String ship() throws Exception{
        List<Items> cart = itemsSvc.getCart();
        List<String> items = new LinkedList<>();
        Quotation quote = new Quotation();
        for(Items i:cart){
            items.add(i.getName());
        }
        quote = quotationService.getQuotations(items);
        return "view2";
    }


    
}

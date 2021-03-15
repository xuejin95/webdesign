package com.example.rentsummary.controller;

import com.example.rentsummary.server.RentContextGet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RentController {

    @GetMapping(value = "getrent")
    @ResponseBody
    public String getRent() {
//        return RentContextGet.getRent();
//        return RentContextGet.getRent();
        return RentContextGet.getRentFromAllHomes();
//        return RentContextGet.getRentFromDomain();
    }

    @GetMapping(value = "/")
    public String toIndex(){
        return "rentsummary";
    }

    @GetMapping(value = "/test")
    @ResponseBody
    public String test(){
        return "rentsummary";
    }

    @GetMapping(value = "/allhomes")
    public String allhomes(){
        return "allhomes";
    }
    @GetMapping(value = "/domain")
    public String domain(){
        return "domain";
    }
    @GetMapping(value = "/realestate")
    public String realestate(){
        return "realestate";
    }
    @GetMapping(value = "/zango")
    public String zango(){
        return "zango";
    }



}

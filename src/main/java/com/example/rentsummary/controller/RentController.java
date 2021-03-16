package com.example.rentsummary.controller;

import com.example.rentsummary.server.RentContextGet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RentController {

    @GetMapping(value = "getrent")
    public String getRent(Model model) {

        model.addAttribute("allhomes",RentContextGet.getRentFromAllHomes());
        model.addAttribute("domain",RentContextGet.getRentFromDomain());
        model.addAttribute("zango",RentContextGet.getRentFromzango());
        model.addAttribute("realestate",RentContextGet.getRentFromRealestate());

        return "rentsummary";
//        return RentContextGet.getRent();
//        return RentContextGet.getRent();
//        return RentContextGet.getRentFromAllHomes();
//        return RentContextGet.getRentFromDomain();
    }

    @GetMapping(value = "/")
    public String toIndex(){
        return "rentsummary.html";
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

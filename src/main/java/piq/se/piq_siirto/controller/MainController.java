package piq.se.piq_siirto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import piq.se.piq_siirto.model.Merchant;
import piq.se.piq_siirto.model.MerchantAttributes;
import piq.se.piq_siirto.model.User;

@Controller
public class MainController {

    @GetMapping("/paymentiq/api")
    public String submitForm(Model model, User user, Merchant merchant, MerchantAttributes merchantAttributes){
        return "submitForm";
    }


}

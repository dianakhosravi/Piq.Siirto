package piq.se.piq_siirto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import piq.se.piq_siirto.model.DepositRequest;
import piq.se.piq_siirto.model.User;
import piq.se.piq_siirto.service.IntegrationService;

@Controller
public class MainController {

    @Autowired
    IntegrationService integrationService;

    @GetMapping("/paymentiq/api")
    public String submitForm(Model model, User user){
        return "submitForm";
    }

    @RequestMapping(value = "/paymentiq/api/deposit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DepositRequest> depositRequest(@RequestBody User user) {

        user = integrationService.findUserById(user.getUserId());
        DepositRequest depositRequest = integrationService.findDepositRequest(user.getUserId());

        return ResponseEntity.ok(depositRequest);
    }



}

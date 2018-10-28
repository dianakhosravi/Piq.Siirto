package piq.se.piq_siirto.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import piq.se.piq_siirto.model.*;
import piq.se.piq_siirto.service.IntegrationService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;


@Controller
public class MainController {

    private final String USER_AGENT = "Mozilla/5.0";
    private final String MERCHANT_ID = "100095999";
    private final String USER_ID = "TEST_EUR";

    @Autowired
    IntegrationService integrationService;

    Double currentBalance;

    @GetMapping("/")
    public String  index(Model model,Amount amount){
        model.addAttribute("balance",integrationService.findAccountByUserId(USER_ID).getBalance());
        model.addAttribute("userId",USER_ID);
        return "index";
    }

    @RequestMapping(value = "/deposit", method ={ RequestMethod.GET, RequestMethod.POST })
    public String  deposit(Model model, Amount amount) throws IOException, JSONException {

        String url = "https://test-api.paymentiq.io/paymentiq/api/siirto/deposit/validate";
/*
        provide body to post
*/
        JSONObject data = new JSONObject();
        data.put("sessionId",UUID.randomUUID());
        data.put("userId",USER_ID);
        data.put("merchantId",MERCHANT_ID);
        data.put("amount",amount.getValue());
        data.put("phonenumber","+358509999991");

        JSONObject jsonObject = getResponse(url, data);

        Account account = integrationService.findAccountByUserId(USER_ID);

        if((Boolean) jsonObject.get("success")){
            account.setBalance(account.getBalance()+Double.parseDouble(amount.getValue()));
            integrationService.saveAccount(account);

        }
        integrationService.saveTransaction(Transaction.builder()
                .accountId(account.getAccountId())
                .amount(Double.parseDouble(amount.getValue()))
                .created(LocalDateTime.now())
                .txType(TransactionType.DEPOSIT)
                .build());

        model.addAttribute("transactions",integrationService.getAllTransaction(account.getAccountId()));
        model.addAttribute("balance",account.getBalance());
        model.addAttribute("userId",USER_ID);
        return  "index";
    }


    @RequestMapping(value = "/withdrawal",method ={ RequestMethod.GET, RequestMethod.POST })
    public String withdrawRequest(Model model, Amount amount) throws IOException, JSONException {

        String url = "https://test-api.paymentiq.io/paymentiq/api/siirto/withdrawal/validate";

/*
        provide body to post
*/

        JSONObject data = new JSONObject();
        data.put("sessionId",UUID.randomUUID());
        data.put("userId",USER_ID);
        data.put("merchantId",MERCHANT_ID);
        data.put("amount",amount.getValue());
        data.put("phonenumber","+358509999991");

        JSONObject jsonObject = getResponse(url, data);

        Account account = integrationService.findAccountByUserId(USER_ID);
        Transaction transaction;
        if((Boolean) jsonObject.get("success")){
            account.setBalance(account.getBalance()-Double.parseDouble(amount.getValue()));
            integrationService.saveAccount(account);

            integrationService.saveTransaction(Transaction.builder()
                    .accountId(account.getAccountId())
                    .amount(Double.parseDouble(amount.getValue()))
                    .created(LocalDateTime.now())
                    .txType(TransactionType.WITHDRAWAL)
                    .build());
        }
        model.addAttribute("transactions",integrationService.getAllTransaction(account.getAccountId()));
        model.addAttribute("balance",account.getBalance());
        model.addAttribute("userId",USER_ID);
        return  "index";
    }


    private JSONObject getResponse(String url, JSONObject data) throws IOException, JSONException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type","application/json");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data.toString());
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        return new JSONObject(response.toString());
    }

}

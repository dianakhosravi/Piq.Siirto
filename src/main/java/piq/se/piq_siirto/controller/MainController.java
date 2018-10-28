package piq.se.piq_siirto.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import piq.se.piq_siirto.model.*;
import piq.se.piq_siirto.service.IntegrationService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
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
    public String  index(Model model, Amount amount){

        return "index";
    }

    @PostMapping("/deposit")
    public String  index2(Model model, Amount amount) throws IOException, JSONException {

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

        JSONObject jsonObject = getStream(url, data);

        Account account = integrationService.findAccountByUserId(USER_ID);

        if((Boolean) jsonObject.get("success")){
            account.setBalance(account.getBalance()+Double.parseDouble(amount.getValue()));
        }

        model.addAttribute("transaction",null);
        model.addAttribute("value",amount.getValue());
        return  "redirect:/";
    }

    private JSONObject getStream(String url,JSONObject data) throws IOException, JSONException {
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
/*

    @PostMapping("/")
    public ResponseEntity withdrawRequest(Model model, Amount amount,  User user) throws IOException, JSONException {






        //printing result from response
        System.out.println(response.toString());

        return ResponseEntity.ok(withdrawRequest);
    }
*/


  /*

    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<WithdrawResponse> getWithdrawalResponse(@RequestBody WithdrawRequest withdrawRequest) {
        Account account= integrationService.findAccountByUserId(withdrawRequest.getUser().getUserId());
        Double balance = account.getBalance();

        if(balance + withdrawRequest.getUser().getRequestAmount()>0){
            currentBalance = balance + withdrawRequest.getUser().getRequestAmount();
            MessageBody messageBody = MessageBody.builder().
                    label("Amount deposited to player account")
                    .keys(Arrays.asList(CreditCardDeposit.receiptDepositAccountToCharge.toString(),
                            "receiptDepositAccountToCharge"))
                    .value(withdrawRequest.getUser().getRequestAmount().toString()).build();

            withdrawResponse = WithdrawResponse.builder()
                    .success(true)
                    .txState("Successful")
                    .messageBodyList(Collections.singletonList(messageBody)).build();

            withdrawResponses.add(withdrawResponse);

            transaction = Transaction.builder().amount(currentBalance)
                    .created(LocalDate.now().toString()).state("Successful")
                    .txAmount(withdrawRequest.getUser().getRequestAmount())
                    .txAmountCy("SEK").txType("EutellerWithdraw").build();


            transactions = Transactions.builder().transaction(Collections.singletonList(transaction))
                    .userId(withdrawRequest.getUser().getUserId()).success(true)
                            .merchantId(withdrawRequest.getMerchantId()).build();
            transactionsList.add(transactions);
        }
        else{
            ErrorBody errorBody = ErrorBody.builder().keys(Arrays.asList("creditcarddeposit.status.err_declined_other_reason"))
                    .field("").msg("Invalid CVV").build();

            withdrawResponse = WithdrawResponse.builder()
                    .success(false)
                    .txState("Failed")
                    .errorBodyList(Collections.singletonList(errorBody))
                    .build();

        }

            return  ResponseEntity.ok(withdrawResponse);

    }

    @GetMapping(path = "/api/user/transactions",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transactions(@RequestParam String userId) {

        if(userId!=null) {
            integrationService.findUserById(userId).setTransactionsList(transactionsList);
            return ResponseEntity.ok(transactionsList);
        }
        else return null;
    }

  @GetMapping("/transactionsShow")
    public ModelAndView transactionsShow(@RequestParam String userId) {
        if(userId!=null) {
            integrationService.findUserById(userId).setTransactionsList(transactionsList);
            ModelAndView modelAndView = new ModelAndView("transactionList");

            modelAndView.addObject(transactionsList);
            modelAndView.addObject("UserId", integrationService.findUserById(userId));
            modelAndView.addObject("amount", transaction.getAmount());
            return modelAndView;
        }
        return null;
    }*/


}

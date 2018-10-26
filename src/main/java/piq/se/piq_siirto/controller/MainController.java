package piq.se.piq_siirto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import piq.se.piq_siirto.model.*;
import piq.se.piq_siirto.service.IntegrationService;
import piq.se.piq_siirto.service.TransactionsDao;

import java.time.LocalDate;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    IntegrationService integrationService;

    @Autowired
    TransactionsDao transactionsDao;


    Double currentBalance;

    Set<DepositResponse> depositResponses = new HashSet<>();

    DepositRequest depositRequest;
    DepositResponse depositResponse;
    Transaction transaction;
    Transactions transactions;
    Set<Transactions>transactionsList = new HashSet<>();


    @GetMapping("/paymentiq/api")
    public String submitForm(Model model, User user) {
        return "submitForm";
    }

    @RequestMapping(value = "/paymentiq/api/depositRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DepositRequest> depositRequest(@RequestBody User user) {

        user = integrationService.findUserById(user.getUserId());
        DepositRequest depositRequest = integrationService.findDepositRequest(user.getUserId());

        return ResponseEntity.ok(depositRequest);
    }



    /*@RequestMapping(value = "/p", method = RequestMethod.POST)
    @ResponseBody
    public DepositResponse getDepositResponse() {
        DepositResponse build = DepositResponse.builder()
                .success(false)
                .messageBodyList(Collections.singletonList(
                        MessageBody.builder()
                                .keys(Arrays.asList("receiptDepositAccountToCharge", "llkhkhk", "jhvhgjcjjhg"))
                                .label("")
                                .build())
                )
                .build();


        return build;
    }*/

    @RequestMapping(value = "/paymentiq/api/depositResponse", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<DepositResponse> getDepositResponse(@RequestBody DepositRequest depositRequest) {
        Account account= integrationService.findAccountByUserId(depositRequest.getUser().getUserId());
        Double balance = account.getBalance();

        if(balance + depositRequest.getUser().getRequestAmount()>0){
            currentBalance = balance + depositRequest.getUser().getRequestAmount();
            MessageBody messageBody = MessageBody.builder().
                    label("Amount deposited to player account")
                    .keys(Arrays.asList(CreditCardDeposit.receiptDepositAccountToCharge.toString(),
                            "receiptDepositAccountToCharge"))
                    .value(depositRequest.getUser().getRequestAmount().toString()).build();

            depositResponse = DepositResponse.builder()
                    .success(true)
                    .txState("Successful")
                    .messageBodyList(Collections.singletonList(messageBody)).build();

            depositResponses.add(depositResponse);

            transaction = Transaction.builder().amount(currentBalance)
                    .created(LocalDate.now().toString()).state("Successful")
                    .txAmount(depositRequest.getUser().getRequestAmount())
                    .txAmountCy("SEK").txType("NetellerDeposit").build();


            transactions = Transactions.builder().transaction(Collections.singletonList(transaction))
                    .userId(depositRequest.getUser().getUserId()).success(true)
                            .merchantId(depositRequest.getMerchantId()).build();
            transactionsList.add(transactions);
        }
        else{
            ErrorBody errorBody = ErrorBody.builder().keys(Arrays.asList("creditcarddeposit.status.err_declined_other_reason"))
                    .field("").msg("Invalid CVV").build();

            depositResponse = DepositResponse.builder()
                    .success(false)
                    .txState("Failed")
                    .errorBodyList(Collections.singletonList(errorBody))
                    .build();

        }

            return  ResponseEntity.ok(depositResponse);

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


}

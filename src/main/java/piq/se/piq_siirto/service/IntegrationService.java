package piq.se.piq_siirto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piq.se.piq_siirto.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class IntegrationService {

    @Autowired
    UserDao userDao;

    @Autowired
    DepositReqDao depositReqDao;

    @Autowired
    MerchantAttributesDao merchantAttributesDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    TransactionsDao transactionsDao;

    CreditCardDeposit creditCardDeposit;

    public void initialRepository() {

        User user = User.builder()
                .userId("123456")
                .sessionId("2")
                .requestAmount(100.0)
                .build();
        userDao.save(user);


        MerchantAttributes attributes = MerchantAttributes.builder()
                .bonusCode("A123")
                .channelId("mobile")
                .build();
        merchantAttributesDao.save(attributes);

        DepositRequest depositRequest = DepositRequest.builder()
                .merchantId("1")
                .user(user)
                .attributes(attributes)
                .build();
        depositReqDao.save(depositRequest);

        DepositResponse.builder()
                .txState("Failed")
                .success(false)
                .messageBodyList(Collections.singletonList(
                        MessageBody.builder()
                                .keys(Arrays.asList("receiptDepositAccountToCharge","llkhkhk","jhvhgjcjjhg"))
                                .label("")
                                .build())
                )
                .build();


        Account account = Account.builder()
                .accountId(1)
                .user(user)
                .balance(200.0)
                .build();
        accountDao.save(account);

    }

    public User findUserById(String userId) {
        return (userId != null) ?
                userDao.findAll().stream().
                        filter(c -> c.getUserId().equalsIgnoreCase(userId)).findAny().get() :
                null;

    }

    public DepositRequest findDepositRequest(String userId) {
        return (userId != null) ?
                depositReqDao.findAll().stream().
                        filter(c -> c.getUser().getUserId().equalsIgnoreCase(userId)).findAny().get() :
                null;

    }

    public Account findAccountByUserId (String userId){
        return (userId != null) ?
                accountDao.findAll().stream().
                        filter(c->c.getUser().getUserId().equalsIgnoreCase(userId)).findAny().get():
                null;
    }
    public Transactions findTransactionsListByUserId (String userId){
        return (userId != null) ?
                transactionsDao.findAll().stream().
                        filter(c->c.getUserId().equalsIgnoreCase(userId)).findAny().get():
                null;

    }
}

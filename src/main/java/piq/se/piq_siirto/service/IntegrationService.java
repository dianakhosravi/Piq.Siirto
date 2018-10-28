package piq.se.piq_siirto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piq.se.piq_siirto.Dao.AccountDao;
import piq.se.piq_siirto.Dao.TransactionDao;
import piq.se.piq_siirto.Dao.UserDao;
import piq.se.piq_siirto.model.Account;
import piq.se.piq_siirto.model.Transaction;
import piq.se.piq_siirto.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegrationService {

    @Autowired
    UserDao userDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    TransactionDao transactionDao;

    public void initialRepository() {

        User user = User.builder()
                .userId("TEST_EUR")
                .phoneNumber("+358509999991")
                .build();
        userDao.save(user);


        Account account = Account.builder()
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

    public Account findAccountByUserId(String userId) {
        return (userId != null) ?
                accountDao.findAll().stream().
                        filter(c -> c.getUser().getUserId().equalsIgnoreCase(userId)).findAny().get() :
                null;
    }

    public Account saveAccount(Account account) {
        return accountDao.save(account);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionDao.save(transaction);
    }

    public List<Transaction> getAllTransaction(Long accountId) {
        return accountId != null ?
                transactionDao.findAll().stream()
                        .filter(t -> t.getAccountId() == accountId)
                        .collect(Collectors.toList())
                : null;
    }
}

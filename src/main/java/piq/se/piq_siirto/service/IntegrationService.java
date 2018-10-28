package piq.se.piq_siirto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piq.se.piq_siirto.Dao.AccountDao;
import piq.se.piq_siirto.Dao.UserDao;
import piq.se.piq_siirto.model.Account;
import piq.se.piq_siirto.model.User;

@Service
public class IntegrationService {

    @Autowired
    UserDao userDao;

    @Autowired
    AccountDao accountDao;


    public void initialRepository() {

        User user = User.builder()
                .userId("TEST_EUR")
                .phoneNumber("+358509999991")
                .build();
        userDao.save(user);


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

    public Account findAccountByUserId(String userId) {
        return (userId != null) ?
                accountDao.findAll().stream().
                        filter(c -> c.getUser().getUserId().equalsIgnoreCase(userId)).findAny().get() :
                null;
    }

}

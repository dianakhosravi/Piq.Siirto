package piq.se.piq_siirto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piq.se.piq_siirto.model.*;

@Service
public class IntegrationService {

    @Autowired
    UserDao userDao;

    @Autowired
    DepositReqDao depositReqDao;

    @Autowired
    MerchantAttributesDao merchantAttributesDao;

    public void initialRepository(){

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

    }

    public User findUserById (String userId){
        return (userId!=null)?
                userDao.findAll().stream().
                        filter(c->c.getUserId().equalsIgnoreCase(userId)).findAny().get():
                null;

    }

    public DepositRequest findDepositRequest(String userId){
        return (userId!=null)?
                depositReqDao.findAll().stream().
                        filter(c->c.getUser().getUserId().equalsIgnoreCase(userId)).findAny().get():
                null;

    }
}

package piq.se.piq_siirto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piq.se.piq_siirto.model.*;

@Service
public class IntegrationService {

    @Autowired
    UserDao userDao;

    @Autowired
    MerchantDao merchantDao;

    @Autowired
    MerchantAttributesDao merchantAttributesDao;

    public void initialRepository(){

        User user = User.builder()
                .userId("123456")
                .sessionId("HT04D1QAAAAABQDGPM5QAAA")
                .build();
        userDao.save(user);


        MerchantAttributes merchantAttributes = MerchantAttributes.builder()
                .bonusCode("A123")
                .channelId("mobile")
                .build();
        merchantAttributesDao.save(merchantAttributes);


        Merchant merchant = Merchant.builder()
                .merchantId("1")
                .merchantAttributes(merchantAttributes)
                .build();
        merchantDao.save(merchant);










    }
}

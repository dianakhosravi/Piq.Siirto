package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Builder
public class DepositRequest {

    @Id
    private String merchantId;
    @OneToOne
    private User user;
    @OneToOne
    private MerchantAttributes attributes;

}

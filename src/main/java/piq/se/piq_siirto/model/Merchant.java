package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Builder
public class Merchant {

    @Id
    private String merchantId;

    @OneToOne
    private MerchantAttributes merchantAttributes;

}

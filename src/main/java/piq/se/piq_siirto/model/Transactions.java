package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;
    private String merchantId;
    private String userId;
    private Boolean success;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Transaction>transaction;
}

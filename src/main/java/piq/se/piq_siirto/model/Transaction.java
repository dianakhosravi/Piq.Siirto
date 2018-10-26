package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
public class Transaction {
    private String state;
    private Double txAmount;
    private String txAmountCy;
    @Id
    private String txId;
    private String txRefId;
    private String txType;
    private Double amount;
    private String created;

}

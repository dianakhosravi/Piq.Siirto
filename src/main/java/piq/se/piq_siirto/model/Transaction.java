package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class Transaction {

    @Id
    private String transactionId;
    private String accountId;
    private Double amount;
    private LocalDateTime created;
    private String txType;

}

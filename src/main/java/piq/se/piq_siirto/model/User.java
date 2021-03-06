package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;

@Data
@Builder
@Entity
public class User {
    @Id
    private String userId;
    private String phoneNumber;
    @ElementCollection
    Set<Transaction> transactionsList;



}

package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long accountId;
    private Double balance;
    @OneToOne
    User user;

    @OneToMany
    Set<Transaction> transactions;
}

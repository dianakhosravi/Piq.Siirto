package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@Entity
public class Account {

    @Id
    private Integer accountId;
    private Double balance;
}

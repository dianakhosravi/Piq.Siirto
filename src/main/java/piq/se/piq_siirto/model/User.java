package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@Entity
public class User {
    @Id
    private String userId;
    private String sessionId;
    private Double requestAmount;

}

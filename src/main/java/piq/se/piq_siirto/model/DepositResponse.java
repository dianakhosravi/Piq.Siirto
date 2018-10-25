package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
@Builder
@Entity
public class DepositResponse {

    @Id
    private Integer id;

    private Boolean success;
    private ArrayList<String>messages;
    private ArrayList<String>errors;

}

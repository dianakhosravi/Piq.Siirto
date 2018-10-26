package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@Entity
public class DepositResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Boolean success;
    private String txState;
    @OneToMany()
    private List<MessageBody> messageBodyList;
    @OneToMany()
    private List<ErrorBody> errorBodyList;


}

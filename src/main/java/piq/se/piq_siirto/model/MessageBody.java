package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
public class MessageBody {

    @Id
    @GeneratedValue
    private String id;

    @ElementCollection
    private List<String> keys;
    private String label;
    private String value;


}

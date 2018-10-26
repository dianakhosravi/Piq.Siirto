package piq.se.piq_siirto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
@Builder
public class ErrorBody {

    @Id
    @GeneratedValue
    private String id;

    @ElementCollection
    private List<String> keys;
    private String field;
    private String msg;
}

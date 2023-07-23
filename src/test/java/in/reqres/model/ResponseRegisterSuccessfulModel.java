package in.reqres.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponseRegisterSuccessfulModel {
    private int id;
    private String token;
}


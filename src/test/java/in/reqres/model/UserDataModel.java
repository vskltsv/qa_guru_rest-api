package in.reqres.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDataModel {
    private int total_pages;
    private List<DataClass> data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataClass {
        public int id;

    }
}


package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.junit.jupiter.api.Test;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseUserInfo {
    private String name, job;

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}

package pojo_models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

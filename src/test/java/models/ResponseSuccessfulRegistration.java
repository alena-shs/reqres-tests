package models;

import lombok.Data;

@Data
public class ResponseSuccessfulRegistration {
    private Integer id;
    private String token;

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}

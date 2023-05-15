package models;

import lombok.Data;

@Data
public class ResponseSuccessfuLogin {
    private String token;

    public String getToken() {
        return token;
    }
}

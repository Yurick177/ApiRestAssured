package api.pojo;

import lombok.Data;

@Data
public class Register {

    private String email;
    private String password;

    public Register(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

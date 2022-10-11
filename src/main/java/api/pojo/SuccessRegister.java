package api.pojo;

import lombok.Data;

@Data
public class SuccessRegister {

    public Integer id;
    public String token;

    public SuccessRegister(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

}

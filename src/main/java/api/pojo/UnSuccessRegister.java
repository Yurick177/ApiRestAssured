package api.pojo;

import lombok.Data;

@Data
public class UnSuccessRegister {
    private String error;

    public UnSuccessRegister(String error) {
        this.error = error;
    }

}

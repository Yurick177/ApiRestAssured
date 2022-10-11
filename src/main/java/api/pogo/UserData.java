package api.pogo;

import lombok.Data;

@Data
public class UserData {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;

    public UserData(Integer id, String email, String firstName, String lastName, String avatar) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

}

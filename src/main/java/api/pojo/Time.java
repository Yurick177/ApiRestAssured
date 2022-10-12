package api.pojo;

import lombok.Data;

@Data
public class Time {

    private String name;
    private String job;

    public Time(String name, String job) {
        this.name = name;
        this.job = job;
    }

}

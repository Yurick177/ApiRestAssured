package api.pojo;

public class TimeResponse extends Time {

    private String updatedAt;

    public TimeResponse(String name, String job, String updatedAt) {
        super(name, job);
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}

import api.pojo.*;
import api.specifications.Specifications;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresTest {

    private final static String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIdTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
//                .contentType(ContentType.JSON)
                .get(URL + "api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        users.forEach(x -> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assertions.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());
        for (int i = 0; i < avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i)));
        }

    }

    @Test
    public void singleUserTest() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "api/users/2")
                .then().log().all()
                .extract().body().jsonPath().get("data");

    }

    @Test
    public void successRegisterTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register register = new Register("eve.holt@reqres.in", "pistol");
        SuccessRegister successRegister = given()
                .body(register)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessRegister.class);
        Assertions.assertNotNull(successRegister.getId());
        Assertions.assertNotNull(successRegister.getToken());
        Assertions.assertEquals(id, successRegister.getId());
        Assertions.assertEquals(token, successRegister.getToken());


    }

    @Test
    public void unSuccessRegisterTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecError400());
        Register register = new Register("sydney@fife", "");
        UnSuccessRegister unSuccessRegister = given()
                .body(register)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessRegister.class);
        Assertions.assertEquals("Missing password", unSuccessRegister.getError());

    }

    @Test
    public void sortedYearsTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<ColorsData> colorsData = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);
        List<Integer> years = colorsData.stream().map(ColorsData::getYear).collect(Collectors.toList());
        List<Integer> yearsSort = years.stream().sorted().collect(Collectors.toList());
        Assertions.assertEquals(yearsSort, years);
    }

    @Test
    public void deleteUserTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        given()
                .when()
                .delete("api/users/2")
                .then().log().all();

    }

    @Test
    public void timeTest() {
        Specifications.installSpecifications(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        Time time = new Time("morpheus", "zion resident");
        TimeResponse timeResponse = given()
                .body(time)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(TimeResponse.class);

        String regex = "(.{5})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");
        System.out.println(currentTime);
        Assertions.assertEquals(currentTime, timeResponse.getUpdatedAt().replaceAll(regex, ""));
        System.out.println(timeResponse.getUpdatedAt().replaceAll(regex, ""));
    }

}

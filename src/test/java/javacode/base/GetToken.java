package javacode.base;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
public class GetToken extends BaseTest {
    @Test(description = "Запрос на получение токена canGetAccessToken() ")
    public void canGetAccessToken() {

        Map<String,String> requestAuthData = new HashMap<>();
        requestAuthData.put("username", properties.getProperty("username"));
        requestAuthData.put("password", properties.getProperty("password"));

        Response response = given()
                .spec(requestSpec)
                .body(requestAuthData)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, "Токен не получен!");

        setAccessToken(token);

        System.out.println("Токен получен! " +token);
    }

}

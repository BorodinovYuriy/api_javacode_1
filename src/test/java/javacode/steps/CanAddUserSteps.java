package javacode.steps;

import com.mongodb.client.MongoCollection;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import javacode.data.Data;
import javacode.helper.MongoDBHelper;
import org.bson.Document;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class CanAddUserSteps extends BaseSteps implements BaseMethods{
    Response response = null;
    MongoDBHelper mongo;

    public CanAddUserSteps(MongoDBHelper mongo) {
        this.mongo = mongo;
    }

    @Step("Отправить POST запрос https://aqa-api.javacode.ru/api/user-auth1")
    @Override
    public CanAddUserSteps sendPOST(
            Properties properties,
            RequestSpecification requestSpec,
            String token) {

        Map<String,String> user = new HashMap<>();
        user.put("first_name", Data.makeName());
        user.put("surname", Data.makeSurname());
        user.put("email", Data.makeEmail());
        user.put("username", Data.makeUsername());
        user.put("plain_password", Data.makePassword());
        user.put("roles", "admin");

        System.out.println(user);

        response = given()
                .spec(requestSpec)
                .header("Authorization",token)
                .body(user)
                .when()
                .post("/api/user-auth1");
        return this;
    }
    @Step("Проверить код состояния 200")
    @Override
    public CanAddUserSteps stat200() {
        status200(response);
        return this;
    }
    @Step("Проверить тело ответа от сервера")
    @Override
    public CanAddUserSteps checkBodyJSON(ResponseSpecification responseSpec) {
        checkJsonAndResponse(
                response,
                responseSpec,
                "add_user_response_schema.json");
        return this;
    }
    @Step("Подключиться к базе и сравнить данные")
    @Override
    public void checkInMongo(String collectionName) {
        int userIdFromResponse = response.jsonPath().getInt("data._id");
        String surnameFromResponse = response.jsonPath().getString("data.surname");
        String firstNameFromResponse = response.jsonPath().getString("data.first_name");
        String userNameFromResponse = response.jsonPath().getString("data.username");
        String emailFromResponse = response.jsonPath().getString("data.email");
        String passwordFromResponse = response.jsonPath().getString("data.password");
        List<String> rolesFromResponse = response.jsonPath().getList("data.roles");

        MongoCollection<Document> userCollection = mongo.getCollection(collectionName);
        Document userDocument = userCollection.find(new Document("_id", userIdFromResponse)).first();

        Assert.assertNotNull(userDocument,"User not found in MongoDB");
        Assert.assertEquals(userIdFromResponse, userDocument.getInteger("_id"), "User ID mismatch");
        Assert.assertEquals(surnameFromResponse,userDocument.getString("surname"),"surname mismatch");
        Assert.assertEquals(firstNameFromResponse,userDocument.getString("first_name"),"first_name mismatch");
        Assert.assertEquals(userNameFromResponse,userDocument.getString("username"),"username mismatch");
        Assert.assertEquals(emailFromResponse,userDocument.getString("email"),"email mismatch");
        Assert.assertEquals(passwordFromResponse,userDocument.getString("password"),"password mismatch");
        Assert.assertNotNull(response.jsonPath().getList("data.roles"),"roles is empty");

        // TODO: 05.02.2025 добавить остальные проверки...
        System.out.println("Data comparison with MongoDB successful!");
    }
}

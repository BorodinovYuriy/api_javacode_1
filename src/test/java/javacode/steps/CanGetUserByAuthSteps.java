package javacode.steps;
//Отправить POST запрос https://aqa-api.javacode.ru/api/auth/login
//Проверить код состояния
//Проверить тело ответа от сервера
//Подключиться к базе и сравнить данные

import com.mongodb.client.MongoCollection;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import javacode.helper.MongoDBHelper;
import org.bson.Document;
import org.testng.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class CanGetUserByAuthSteps {

    Response response = null;
    MongoDBHelper mongo;

    public CanGetUserByAuthSteps(MongoDBHelper mongo) {
        this.mongo = mongo;
    }
    @Step("Отправить POST запрос https://aqa-api.javacode.ru/api/auth/login")
    public CanGetUserByAuthSteps sendPOSTAuth(Properties properties, RequestSpecification requestSpec){

        Map<String,String> requestAuthData = new HashMap<>();
        requestAuthData.put("username", properties.getProperty("username"));
        requestAuthData.put("password", properties.getProperty("password"));

         response = given()
                    .spec(requestSpec)
                    .body(requestAuthData)
                    .when()
                    .post("/api/auth/login");
        return this;
    }
    @Step("Проверить код состояния")
    public CanGetUserByAuthSteps stat200(){
        if (response != null){
            response
                    .then()
                    .statusCode(200);
        }
        return this;
    }
    @Step("Проверить тело ответа от сервера")
    public CanGetUserByAuthSteps checkBodyJSON(ResponseSpecification responseSpecification){
        if (response != null){
            response
                    .then()
                    .spec(responseSpecification)
                    .assertThat()
                    .body(matchesJsonSchema(
                            new File("src/test/resources/json_schema/user_auth_schema.json"))
                    );
        }
        return this;
    }
    @Step("Подключиться к базе и сравнить данные")
    public void checkInMongo(String collectionName) {
        int userIdFromResponse = response.jsonPath().getInt("user._id");
        String usernameFromResponse = response.jsonPath().getString("user.username");
        String emailFromResponse = response.jsonPath().getString("user.email");

        MongoCollection<Document> userCollection = mongo.getCollection(collectionName);
        Document userDocument = userCollection.find(new Document("_id", userIdFromResponse)).first();

        Assert.assertNotNull(userDocument,"User not found in MongoDB");
        Assert.assertEquals(userIdFromResponse, userDocument.getInteger("_id"), "User ID mismatch");
        Assert.assertEquals(usernameFromResponse, userDocument.getString("username"), "Username mismatch");
        Assert.assertEquals(emailFromResponse, userDocument.getString("email"), "Email mismatch");
        // TODO: 05.02.2025 добавить остальные проверки...
        System.out.println("Data comparison with MongoDB successful!");
    }
}

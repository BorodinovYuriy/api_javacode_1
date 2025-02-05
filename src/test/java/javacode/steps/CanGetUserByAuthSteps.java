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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class CanGetUserByAuthSteps extends BaseSteps implements BaseMethods{

    Response response = null;

    public CanGetUserByAuthSteps(MongoDBHelper mongo) {
        super.mongo = mongo;
    }
    @Step("Отправить POST запрос https://aqa-api.javacode.ru/api/auth/login")
    @Override
    public CanGetUserByAuthSteps sendPOST(Properties properties, RequestSpecification requestSpec, String token){

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
    @Override
    public CanGetUserByAuthSteps stat200(){
        status200(response);
        return this;
    }
    @Step("Проверить тело ответа от сервера")
    @Override
    public CanGetUserByAuthSteps checkBodyJSON(ResponseSpecification responseSpecification){
        checkJsonAndResponse(
                response,
                responseSpecification,
                "user_auth_schema.json");
        return this;
    }
    @Step("Подключиться к базе и сравнить данные")
    @Override
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

package javacode.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import javacode.helper.MongoDBHelper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.util.Properties;


public class BaseTest {

    protected static String baseURI;
    protected static String accessToken;
    protected static Properties properties;
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    protected static  MongoDBHelper mongo;

    @BeforeClass
    static void setup() throws IOException {
        properties = new Properties();
        properties.load(BaseTest.class.getClassLoader().getResourceAsStream("application.properties"));
        baseURI = properties.getProperty("baseURI");
        RestAssured.baseURI = baseURI;
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseURI)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();

    }
    protected static void setAccessToken(String token) {
        accessToken = token;
    }
    protected String getAccessToken() {
        return accessToken;
    }
    @AfterClass
    private void closeMongo(){
        if(mongo != null){
            mongo.close();
        }

    }
}


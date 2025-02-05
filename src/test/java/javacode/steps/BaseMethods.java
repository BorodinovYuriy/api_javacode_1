package javacode.steps;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Properties;

public interface BaseMethods {
    <T extends BaseSteps> T sendPOST(Properties properties, RequestSpecification requestSpec, String token);
    <T extends BaseSteps> T stat200();
    <T extends BaseSteps> T checkBodyJSON(ResponseSpecification responseSpec);
    void checkInMongo(String collectionName);
}



package javacode.steps;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Properties;

public class CanAddTemplateSteps extends BaseSteps implements BaseMethods{

    @Override
    public <T extends BaseSteps> T sendPOST(Properties properties, RequestSpecification requestSpec, String token) {
        return null;
    }

    @Override
    public <T extends BaseSteps> T stat200() {
        return null;
    }

    @Override
    public <T extends BaseSteps> T checkBodyJSON(ResponseSpecification responseSpec) {
        return null;
    }

    @Override
    public void checkInMongo(String collectionName) {

    }
}

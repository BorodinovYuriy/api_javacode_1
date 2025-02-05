package javacode.steps;

import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import javacode.helper.MongoDBHelper;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class BaseSteps {
    protected MongoDBHelper mongo;
    protected void status200(Response response){
        if (response != null){
            response
                    .then()
                    .statusCode(200);
        }

    }
    protected void checkJsonAndResponse(Response response,ResponseSpecification responseSpec, String json_schema){
        if (response != null){
            response
                    .then()
                    .spec(responseSpec)
                    .assertThat()
                    .body(matchesJsonSchema(
                            new File("src/test/resources/json_schema/"
                                    +json_schema))
                    );
        }
    }
}

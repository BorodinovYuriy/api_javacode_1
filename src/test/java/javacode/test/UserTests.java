package javacode.test;

import javacode.base.BaseTest;
import javacode.base.GetToken;
import javacode.helper.MongoDBHelper;
import javacode.steps.CanGetUserByAuthSteps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserTests extends BaseTest {


    private String token;

    @BeforeMethod(description = "Получение токена если null")
    void getToken(){
        if (accessToken == null){
            GetToken authTests = new GetToken();
            authTests.canGetAccessToken();
        }
        token = getAccessToken();
    }

    @BeforeMethod(description = "Подключение к mongo DB")
    void setMongo(){
        if (mongo == null){
            mongo = new MongoDBHelper(properties);
        }
    }
//------------------------------------------------------------------------------------//
    @Test(description = "Авторизация на портале")
    void canGetUserByLogin() {
        CanGetUserByAuthSteps auth = new CanGetUserByAuthSteps(mongo);
        auth
                .sendPOSTAuth(properties, requestSpec)
                .stat200()
                .checkBodyJSON(responseSpec)
                .checkInMongo(properties.getProperty("mongoCollectionUsers"));
    }
    @Test
    void test1(){}
    @Test
    void test2(){}
    @Test
    void test3(){}
    @Test
    void test4(){}
    @Test
    void test5(){}
    @Test
    void test6(){}



}

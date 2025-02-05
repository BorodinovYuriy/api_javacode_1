package javacode.test;

import javacode.base.BaseTest;
import javacode.base.GetToken;
import javacode.helper.MongoDBHelper;
import javacode.steps.CanAddUserSteps;
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
//----------------------------------
    @Test(description = "Авторизация на портале")
    void canGetUserByLogin() {
        CanGetUserByAuthSteps auth = new CanGetUserByAuthSteps(mongo);
            auth
                    .sendPOST(properties, requestSpec, token)
                    .stat200()
                    .checkBodyJSON(responseSpec)
                    .checkInMongo(properties.getProperty("mongoCollectionUsers"));
    }
    @Test(description = "Добавление нового пользавателя")
    void canAddUser(){
        CanAddUserSteps addUser = new CanAddUserSteps(mongo);
            addUser
                    .sendPOST(properties, requestSpec, token)
                    .stat200()
                    .checkBodyJSON(responseSpec)
                    .checkInMongo(properties.getProperty("mongoCollectionUsers"));

    }
    @Test(description = "Добавление вопроса")
    public void canAddQuestion() {
    }

    @Test(description = "Редактирование вопроса")
    public void canEditQuestion() {
    }

    @Test(description = "Добавление квиза")
    public void canAddQuiz() {
    }

    @Test(description = "Добавление модуля")
    public void canAddModule() {
    }

    @Test(description = "Добавление курса")
    public void canAddCurse() {
    }

    @Test(description = "Добавление экзамена")
    public void canAddExam() {
    }

    @Test(description = "Добавление темплейта")
    public void canAddTemplate() {
    }

    @Test(description = "Авторизация с неверным логином или паролем")
    public void wrongCredential() {
    }



}

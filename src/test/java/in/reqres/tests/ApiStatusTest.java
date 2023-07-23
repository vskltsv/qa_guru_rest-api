package in.reqres.tests;


import in.reqres.model.CreateUserResponseModel;
import in.reqres.model.RequestUserModel;
import in.reqres.model.UserDataModel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static in.reqres.spec.UserSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Story("Тестовое задание API")
@Tag("remoteApi")
public class ApiStatusTest extends BaseApiClassTest {



    @Test
    @DisplayName("Проверка получения пользователей по url - https://reqres.in/api/users?page=2")
    void userTest() {
        UserDataModel userData = step("Запрос на /api/users?page=2", () ->
                given(userRequestSpec)
                        .log().all()
                        .param("page", 2)
                        .when()
                        .get("/api/users?page=2")
                        .then()
                        .log().all()
                        .spec(userResponseSpec)
                        .extract().as(UserDataModel.class));


        step("Проверка ответа", () -> {
            assertEquals(2, userData.getTotal_pages());
            assertEquals(7, userData.getData().get(0).getId());
        });


    }
    @Test
    @DisplayName("Проверка создания пользователя по url - https://reqres.in/api/users")
    void createUserSuccessTest() {
        RequestUserModel requestUserModel = new RequestUserModel();
        requestUserModel.setName("morpheus");
        requestUserModel.setJob("leader");

        CreateUserResponseModel responseUser = step("Make request", () ->
                given(userRequestSpec)
                        .body(requestUserModel)
                        .when()
                        .post("api/users")
                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверка ответа", () -> {
            assertEquals("morpheus", responseUser.getName());
            assertEquals("leader", responseUser.getJob());
        });
    }
}

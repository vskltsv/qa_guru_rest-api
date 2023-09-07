package in.reqres.tests;


import in.reqres.model.*;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static in.reqres.spec.BaseSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Story("Тестовое задание API")
@Tag("test")
public class ApiStatusTest extends BaseApiClassTest {

    @Test
    @Tag("positive")
    void userTest() {
        UserDataModel userData = step("Запрос на /api/users?page=2", () ->
                given(userRequestSpec)
                        .log().all()
                        .param("page", 2)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .log().all()
                        .spec(userResponseSpec200)
                        .extract().as(UserDataModel.class));


        step("Проверка ответа", () -> {
            assertEquals(2, userData.getTotal_pages());
            assertEquals(7, userData.getData().get(0).getId());
        });


    }

    @Test
    @Tag("positive")
    void createUserSuccessTest() {
        RequestUserModel requestUserModel = new RequestUserModel();
        requestUserModel.setName("morpheus");
        requestUserModel.setJob("leader");

        CreateUserResponseModel responseUser = step("Make request", () ->
                given(userRequestSpec)
                        .body(requestUserModel)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createUserResponseSpec201)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверка ответа", () -> {
            assertEquals("morpheus", responseUser.getName());
            assertEquals("leader", responseUser.getJob());
        });
    }

    @Test
    @Tag("positive")
    void registerSuccessfulTest() {
        RequestRegisterSuccessfulModel registerData = new RequestRegisterSuccessfulModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        ResponseRegisterSuccessfulModel response = step("Make request", () ->
                given(userRequestSpec)
                        .body(registerData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(registerResponseSpec200)
                        .extract().as(ResponseRegisterSuccessfulModel.class));

        step("Проверка ответа", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));

    }


    @Test
    @Tag("negative")
    void registerUnsuccessful() {
        RequestRegisterSuccessfulModel registerData = new RequestRegisterSuccessfulModel();
        registerData.setEmail("sydney@fife");

        RegisterResponseErrorModel registerResponse = step("Make request", () ->
                given(userRequestSpec)
                        .body(registerData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(missingPassword400Spec)
                        .extract().as(RegisterResponseErrorModel.class));

        step("Проверка ответа", () ->
                assertEquals("Missing password", registerResponse.getError()));
    }


    @Test
    @Tag("positive")
    void deleteUserByIdTest() {

        given()
                .spec(userRequestSpec)
                .when()
                .pathParam("id", "2")
                .delete("/users/{id}")
                .then()
                .spec(response204);
    }

    @Test
    @Tag("positive")
    void updateUserSuccess() {
        RequestUserModel requestData = new RequestUserModel();
        requestData.setName("morpheus");
        requestData.setJob("zion resident");

        UpdateUserResponseModel responseUser = step("Make request", () ->
                given(userRequestSpec)
                        .body(requestData)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(updateUserResponse200Spec)
                        .extract().as(UpdateUserResponseModel.class));

        step("check response", () -> {
            assertEquals("morpheus", responseUser.getName());
            assertEquals("zion resident", responseUser.getJob());
        });

    }
}

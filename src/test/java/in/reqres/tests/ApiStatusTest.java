package in.reqres.tests;


import in.reqres.model.UserDataModel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static in.reqres.spec.UserSpec.userRequestSpec;
import static in.reqres.spec.UserSpec.userResponseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Story("Тестовое задание API")
@Tag("remoteApi")
public class ApiStatusTest extends BaseApiClassTest {



    @Test
    @DisplayName("Проверка JSON-схемы")
    void userTest() {
        UserDataModel userData = (UserDataModel) step("Запрос на /api/users?page=2 ", () ->
                given(userRequestSpec)
                        .log().all()
                        .param("page", 2)
                        .when()
                        .get("/api/users?page=2")
                        .then()
                        .log().all()
                        .spec(userResponseSpec)
                        .extract().as(UserDataModel.class));


        step("Check response", () -> {
            assertEquals(2, userData.getTotal_pages());
            assertEquals(7, userData.getData().get(0).getId());
        });


    }
}

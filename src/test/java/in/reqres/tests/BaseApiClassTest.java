package in.reqres.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.filters;

public class BaseApiClassTest {
    @BeforeEach
    void beforeEach() {
        filters(new AllureRestAssured());
        baseURI = "https://reqres.in/";
    }
}

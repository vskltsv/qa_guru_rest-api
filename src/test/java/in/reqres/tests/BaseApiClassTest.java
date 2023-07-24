package in.reqres.tests;

import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.baseURI;

public class BaseApiClassTest {
    @BeforeEach
    void beforeEach() {
        baseURI = "https://reqres.in/";
    }
}

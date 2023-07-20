package in.reqres;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


public class ApiStatusTest {
    @Test
    void successfulLoginTest() {
        String authData = "{\n    \"name\": \"morpheus\",\n    \"job\": \"zion resident\"\n}"; // BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"),
                "job", is("zion resident"));

    }

    @Test
    void missingPasswordTest() {
        String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"\" }"; // BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void missingEmailTest() {
        String authData = "{ \"email\": \"\", \"password\": \"cityslicka\" }"; // BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void negative404Test() {
        String authData = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; // BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authData)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void usersListUsersTest() {
        given()
                .log().all()
                .param("page", 1)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", hasSize(6))
                .body("total_pages", is(2));
    }
    @Test
    void deleteUsersTest() {
        given()
                .log().all()
                .param("page", 1)
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(204);

    }
}



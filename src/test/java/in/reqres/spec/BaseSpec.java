package in.reqres.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

public class BaseSpec {
    public static RequestSpecification userRequestSpec = with()
            .log().uri()
            .log().method()
            .log().body()
            .basePath("/api")
            .contentType(JSON);

    public static ResponseSpecification userResponseSpec200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemas/responseUser.json"))
            .build();


    public static ResponseSpecification createUserResponseSpec201 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)

            .expectStatusCode(201)
            .expectBody(matchesJsonSchemaInClasspath("schemas/create-user-schema.json"))
            .expectBody("id", notNullValue())
            .expectBody("createdAt", notNullValue())
            .build();

    public static ResponseSpecification registerResponseSpec200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemas/register-user-schema.json"))
            .expectBody("id", notNullValue())
            .expectBody("token", notNullValue())
            .build();
}

package aletca;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

public class ReqresTest {

    @Test
    void successfulLogin() {

        String data = "{\"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\"}";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void negativLogin() {

        String data = "{\"email\": \"eve.holt@reqres.in\"}";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void singleUserNotFound() {

        String data = "{\"email\": \"\", " +
                "\"password\": \"\"}";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    void postCreate201Error() {

        String data = "{ \"name\": \"morpheus\", " +
                "\"job\": \"leader\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"));
    }

    String data = "{\"name\": \"morpheus\", " +
            "\"job\": \"zion resident\"}";

    @Test
    void updateTest() {
        given()
                .body(data)
                .contentType(JSON)
                .when()
                .put("https://reqres.in/api/users")
                .then()
                .body("job", is("zion resident"));
    }

    @Test
    void getDelayedResponse() {
        given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .statusCode(200)
                .body("total", is(12))
                .body("support.url", is("https://reqres.in/#support-heading"))
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    void deleteTest() {
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }
}

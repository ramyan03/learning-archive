package com.fdmgroup.module3;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;

public class PostsMethodsModule3 {

    private static final String BASE_URL = "http://localhost:3000";

    // a. GET all plants
    public void performGETPost() {
        given()
            .contentType(ContentType.JSON)
            .when()
            .get(BASE_URL + "/plants")
            .then()
            .assertThat()
            .statusCode(200)
            .body("size()", equalTo(4))
            .body("[0].name", equalTo("aloe vera"));
    }

    // b. GET one plant by path parameter
    public void performGETPostPathParameter(String id) {
        given()
            .contentType(ContentType.JSON)
            .pathParam("id", id)
            .when()
            .get(BASE_URL + "/plants/{id}")
            .then()
            .assertThat()
            .statusCode(200)
            .body("id", equalTo(id))
            .body("name", equalTo("aloe vera"));
    }

    // c. GET one plant by query parameter
    public void performGETPostQueryParameter(String paramName, String paramValue) {
        given()
            .contentType(ContentType.JSON)
            .queryParam(paramName, paramValue)
            .when()
            .get(BASE_URL + "/plants")
            .then()
            .assertThat()
            .statusCode(200)
            .body("[0].name", equalTo(paramValue));
    }

    // d. POST new plant
    public String performPOSTPost(String name, String price) {
        HashMap<String, String> postContent = new HashMap<>();
        postContent.put("name", name);
        postContent.put("price", price);

        return given()
                .contentType(ContentType.JSON)
                .body(postContent)
                .when()
                .post(BASE_URL + "/plants")
                .then()
                .assertThat()
                .statusCode(201)
                .body("name", equalTo(name))
                .extract().path("id");
    }

    // e. PUT update plant
    public void performPUTPost(String id) {
        String body = """
            {
                "id": "%s",
                "name": "updated plant",
                "price": 99.99
            }
            """.formatted(id);

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .when()
            .put(BASE_URL + "/plants/" + id)
            .then()
            .assertThat()
            .statusCode(200)
            .body("name", equalTo("updated plant"))
            .body("price", equalTo(99.99f));
    }

    // f. DELETE plant
    public void performDELETEPost(String id) {
        given()
            .contentType(ContentType.JSON)
            .when()
            .delete(BASE_URL + "/plants/" + id)
            .then()
            .assertThat()
            .statusCode(200);
    }
}
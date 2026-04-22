package com.fdmgroup.module4;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PostsMethodsWithSerializeDeserialize {

    private static final String BASE_URL = "http://localhost:3000";

    // a. GET all plants — deserialize into List<Post>
    public void performGETPost() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + Authorization.getAccessToken())
                .when()
                .get(BASE_URL + "/plants")
                .then()
                .log().all()
                .extract().response();

        // Deserialization
        List<Post> posts = response.jsonPath().getList("", Post.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(posts, not(empty()));
        assertThat(posts.get(0).getName(), equalTo("aloe vera"));
    }

    // b. GET one plant by path parameter — deserialize into Post
    public void performGETPostPathParameter(String id) {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + Authorization.getAccessToken())
                .pathParam("id", id)
                .when()
                .get(BASE_URL + "/plants/{id}")
                .then()
                .log().all()
                .extract().response();

        // Deserialization
        Post post = response.as(Post.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(post.getId(), equalTo(id));
    }

    // c. GET plant by query parameter — deserialize into Post[]
    public void performGETPostQueryParameter(String paramName, String paramValue) {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + Authorization.getAccessToken())
                .queryParam(paramName, paramValue)
                .when()
                .get(BASE_URL + "/plants")
                .then()
                .log().all()
                .extract().response();

        // Deserialization — encapsulated in array
        Post[] posts = response.as(Post[].class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(posts[0].getName(), equalTo(paramValue));
    }

    // d. POST new plant — serialize using HashMap
    public String performPOSTPost(String name, String price) {
        HashMap<String, String> postContent = new HashMap<>();
        postContent.put("name", name);
        postContent.put("price", price);

        Response response = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + Authorization.getAccessToken())
            .body(postContent)
            .when()
            .post(BASE_URL + "/plants")
            .then()
            .log().all()
            .extract().response();

        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.jsonPath().getString("name"), equalTo(name));

        return response.jsonPath().getString("id");
    }

    // e. PUT update plant — serialize using POJO
    public void performPUTPost(String id) {
        // Serialization via POJO
        Post updatedPost = new Post(id, "updated plant", 99.99);

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + Authorization.getAccessToken())
                .body(updatedPost)
                .when()
                .put(BASE_URL + "/plants/" + id)
                .then()
                .log().all()
                .extract().response();

        // Deserialization
        Post result = response.as(Post.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(result.getName(), equalTo("updated plant"));
        assertThat(result.getPrice(), equalTo(99.99));
    }

    // f. DELETE plant
    public void performDELETEPost(String id) {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + Authorization.getAccessToken())
                .when()
                .delete(BASE_URL + "/plants/" + id)
                .then()
                .log().all()
                .extract().response();

        assertThat(response.statusCode(), equalTo(200));
    }
}
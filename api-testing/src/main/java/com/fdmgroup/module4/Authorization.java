package com.fdmgroup.module4;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Authorization {

    private static final String BASE_URL = "http://localhost:3000";
    private static String accessToken;

    public static String getAccessToken() {
        
        return "fake-token";
    }
}
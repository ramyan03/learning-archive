package com.fdmgroup.module4;

public class RunnerModule4 {

    public static void main(String[] args) {
        PostsMethodsWithSerializeDeserialize posts =
                new PostsMethodsWithSerializeDeserialize();

        // a. GET all plants
        posts.performGETPost();

        // b. GET by path parameter
        posts.performGETPostPathParameter("1");

        // c. GET by query parameter
        posts.performGETPostQueryParameter("name", "aloe vera");

        String newId = posts.performPOSTPost("sunflower", "9.99");
        posts.performPUTPost(newId);
        posts.performDELETEPost(newId);
    }
}
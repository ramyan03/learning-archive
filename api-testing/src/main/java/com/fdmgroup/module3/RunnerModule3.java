package com.fdmgroup.module3;

import java.util.HashMap;

public class RunnerModule3 {

    public static void main(String[] args) {
        PostsMethodsModule3 posts = new PostsMethodsModule3();

        posts.performGETPost();
        System.out.println("GET all plants - PASSED");

        posts.performGETPostPathParameter("1");
        System.out.println("GET plant by path param - PASSED");

        posts.performGETPostQueryParameter("name", "aloe vera");
        System.out.println("GET plant by query param - PASSED");

        String newId = posts.performPOSTPost("sunflower", "9.99");
        System.out.println("POST new plant - PASSED, ID: " + newId);

        posts.performPUTPost(newId);
        System.out.println("PUT update plant - PASSED");

        posts.performDELETEPost(newId);
        System.out.println("DELETE plant - PASSED");
    }
}

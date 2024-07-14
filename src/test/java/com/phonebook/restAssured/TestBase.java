package com.phonebook.restAssured;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidmFsaWRAZW1haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3MjEzOTk5MTYsImlhdCI6MTcyMDc5OTkxNn0.vujiggbkcxCuEK48MM71HkB91EYxgeMQ2VvjxW_vRfA";
    public static final String AUTH = "Authorization";


    @BeforeMethod
    public void init(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }
}

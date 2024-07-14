package com.phonebook.okhttp;

import com.google.gson.Gson;
import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ResponseMessageDto;
import com.phonebook.restAssured.TestBase;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddContactOkhttpTests extends TestBase {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidmFsaWRAZW1haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3MjEzMjA0MzUsImlhdCI6MTcyMDcyMDQzNX0.hnylJcvSAxl3pIWS917wLW4AC4awcnNckudz65L1c3I";

    @Test
    public void AddContactSuccessTest() throws IOException {

        ContactDto contactDto = ContactDto.builder()
                .name("Anna")
                .lastName("West")
                .email("anna@email.com")
                .phone("1112223334")
                .address("Jamaica")
                .description("plays piano")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(contactDto), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(requestBody)
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        String responseJson = response.body().string();

        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        ResponseMessageDto responseMessageDto = gson.fromJson(responseJson, ResponseMessageDto.class);
        String message = responseMessageDto.getMessage();
        System.out.println(message);

        Assert.assertTrue(message.contains("Contact was added!"));

    }

}

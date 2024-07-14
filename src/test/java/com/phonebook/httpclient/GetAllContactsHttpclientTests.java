package com.phonebook.httpclient;

import com.google.gson.Gson;
import com.phonebook.dto.ErrorDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetAllContactsHttpclientTests {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidmFsaWRAZW1haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3MjEzMjA0MzUsImlhdCI6MTcyMDcyMDQzNX0.hnylJcvSAxl3pIWS917wLW4AC4awcnNckudz65L1c3I";
    Gson gson = new Gson();

    @Test
    public void getAllContactsSuccessTest() throws IOException {

        Response response = Request.Get("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .execute();

        HttpResponse httpResponse = response.returnResponse();
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(statusCode, 200);

    }


    @Test
    public void getAllContactsWithWrongTokenTest() throws IOException {

        Response response = Request.Get("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", "qwertyuiop")
                .execute();

        HttpResponse httpResponse = response.returnResponse();
        int statusCode = httpResponse.getStatusLine().getStatusCode();

        Assert.assertEquals(statusCode, 401);

        InputStream inputStream = httpResponse.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);

            ErrorDto errorDto = gson.fromJson(sb.toString(), ErrorDto.class);

            Assert.assertEquals(errorDto.getError(), "Unauthorized");
        }
    }
}

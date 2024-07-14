package com.phonebook.restAssured;

import com.phonebook.dto.AuthRequestDto;
import com.phonebook.dto.AuthResponseDto;
import com.phonebook.dto.ErrorDto;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoginTests extends TestBase{

    AuthRequestDto requestDto = AuthRequestDto.builder()
            .username("valid@email.com")
            .password("ValidPass123$")
            .build();

    @Test
    public void loginSuccessTest(){

        AuthResponseDto dto = given()
                .contentType("application/json")
                .body(requestDto)
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);

        System.out.println(dto.getToken());
    }

    @Test
    public void loginSuccessTest2(){
        String responseToken = given()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("token"))
                .extract().path("token");
        System.out.println(responseToken);
    }

    @Test
    public void loginWrongPasswordTest(){

        ErrorDto errorDto = given()
                .body(AuthRequestDto.builder()
                        .username("valid@email.com")
                        .password("invalidPass123$").build())
                .contentType(ContentType.JSON)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .extract().response().as(ErrorDto.class);
        System.out.println(errorDto.getError());
        System.out.println(errorDto.getMessage());

    }

    @Test
    public void loginWrongPasswordPerfectTest(){

        given()
                .body(AuthRequestDto.builder()
                        .username("valid@email.com")
                        .password("invalidPass123$").build())
                .contentType(ContentType.JSON)
                .when()
                .post("user/login/usernamepassword")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("error", equalTo("Unauthorized"));

    }

}

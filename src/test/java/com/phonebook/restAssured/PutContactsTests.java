package com.phonebook.restAssured;

import com.phonebook.dto.ErrorDto;
import com.phonebook.dto.UpdateContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class PutContactsTests extends TestBase{

    UpdateContactDto dto = UpdateContactDto.builder()
            .id("11664767-a8e6-4066-9042-6e802edb1566")
            .name("Anna")
            .lastName("West")
            .email("anna@email.com")
            .phone("4445556667")
            .address("Jamaica")
            .description("plays piano")
            .build();

    UpdateContactDto dtoWithWrongId = UpdateContactDto.builder()
            .id("11664767-a8e6-4066-9042-6e802edb1561")
            .name("Anna")
            .lastName("West")
            .email("anna@email.com")
            .phone("4445556667")
            .address("Jamaica")
            .description("plays piano")
            .build();

    @Test
    public void updateContactSuccessTest() {
//        String message =
        given()
                .header(AUTH, TOKEN)
                .body(dto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("Contact was updated"));
//                .extract().path("message");

//        System.out.println(message);

    }

    @Test
    public void updateContactWithWrongIdTest() {

//        ErrorDto errorDto =
                given()
                        .header(AUTH, TOKEN)
                        .body(dtoWithWrongId)
                        .contentType(ContentType.JSON)
                        .when()
                        .put("contacts")
                        .then()
                        .assertThat().statusCode(400)
                        .assertThat().body("message", containsString("not found in your contacts!"));
//                .extract().response().as(ErrorDto.class);

    }


    @Test
    public void updateContactWithWrongTokenTest() {

//        ErrorDto errorDto =
        given()
                .header(AUTH, "qwertyuiop")
                .body(dto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("error", containsString("Unauthorized"));
//                .extract().response().as(ErrorDto.class);

    }

}

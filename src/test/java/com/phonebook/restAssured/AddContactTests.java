package com.phonebook.restAssured;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDto;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class AddContactTests extends TestBase {

    ContactDto contactDto = ContactDto.builder()
            .name("Anna")
            .lastName("West")
            .email("anna@email.com")
            .phone("1112223334")
            .address("Jamaica")
            .description("plays piano")
            .build();

    @Test
    public void addContactSuccessTest() {
//        String message =
        given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body(containsString("Contact was added!"));
//                .extract().path("message");
//
//        System.out.println(message);
//        Contact was added! ID: fe9e14a2-e030-4800-a6fa-4195bf31e442
    }

    @Test
    public void addContactWithoutNameTest() {
        ContactDto contactDto1 = ContactDto.builder()
                .lastName("West")
                .email("anna@email.com")
                .phone("1112223334")
                .address("Jamaica")
                .description("plays piano")
                .build();

        ErrorDto errorDto = given()
                .header(AUTH, TOKEN)
                .body(contactDto1)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDto.class);

        Assert.assertTrue(errorDto.getMessage().toString().contains("name=must not be blank"));

    }

    @Test
    public void addContactWithInvalidPhoneTest(){

        ContactDto contactDto2 = ContactDto.builder()
                .name("Anna")
                .lastName("West")
                .email("anna@email.com")
                .phone("1112")
                .address("Jamaica")
                .description("plays piano")
                .build();

//        ErrorDto errorDto =
                given()
                .header(AUTH, TOKEN)
                .body(contactDto2)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(400)
                        .assertThat().body("message.phone",
                                containsString("Phone number must contain only digits! And length min 10, max 15!"));
//                .extract().response().as(ErrorDto.class);


    }

}

package com.phonebook.restAssured;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteContactByIdTests extends TestBase {

    String id;

    @BeforeMethod
    public void precondition() {
        ContactDto contactDto = ContactDto.builder()
                .name("Anna")
                .lastName("West")
                .email("anna@email.com")
                .phone("1112223334")
                .address("Jamaica")
                .description("plays piano")
                .build();

        String message = given()
                .header(AUTH, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract().path("message");
        String[] split = message.split(": ");
        id = split[1];


    }

    @Test
    public void deleteContactByIdTest() {

//        String message =
        given()
                .header(AUTH, TOKEN)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
//                .extract().path("message");
    }

    @Test
    public void deleteContactByWrongIdTest() {

//        ErrorDto errorDto =
                given()
                .header(AUTH, TOKEN)
                .when()
                .delete("contacts/fe9e14a2-e030-4800-a6fa-4195bf31e44")
                .then()
                .assertThat().statusCode(400)
                        .assertThat().body("message", containsString("not found in your contacts!"));
//                .extract().response().as(ErrorDto.class);


    }

}

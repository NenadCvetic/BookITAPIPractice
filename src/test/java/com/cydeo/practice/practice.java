package com.cydeo.practice;

import com.cydeo.utilities.BookITTestBase;
import com.cydeo.utilities.BookITUtils;
import com.cydeo.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

public class practice extends BookITTestBase {


    String finalToken = "";

    @Test
    public void test1 () {


        JsonPath jsonPath = given().queryParam("email", "blyst6@si.edu")
                .and()
                .queryParam("password", "barbabaslyst")
                .and()
                .when()
                .get("/sign")
                .then()
                .statusCode(200)
                .log().all()
                .extract().jsonPath();

        String accessToken = jsonPath.getString("accessToken");

        finalToken = "Bearer " + accessToken;


        given().accept(ContentType.JSON)
                .and()
                .headers("Authorization", finalToken)
                .and()
                .when()
                .get("/api/campuses")
                .then()
                .statusCode(200)
                .and()
                .log().all();


    }



    @Test
    public void test2 () {

        given().accept(ContentType.JSON)
                .and()
                .headers("Authorization", BookITUtils.getTokenByRole("team_leader"))
                .and()
                .when()
                .get("/api/campuses")
                .then()
                .statusCode(200)
                .and()
                .log().all();

    }




}

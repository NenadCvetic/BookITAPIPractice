package com.cydeo.utilities;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
public class BookITUtils {


    public static String getTokenByRole (String role) {

        String email;
        String password;


        switch (role) {

            case "teacher":
                email = ConfigurationReader.getProperty("teacher_email");
                password = ConfigurationReader.getProperty("teacher_password");
                break;

            case "team_member":
                email = ConfigurationReader.getProperty("team_member_email");
                password = ConfigurationReader.getProperty("team_member_password");
                break;

            case "team_leader":
                email = ConfigurationReader.getProperty("team_leader_email");
                password = ConfigurationReader.getProperty("team_leader_password");
                break;

            default:
                throw new IllegalStateException("Invalid role" + role);

        }


        JsonPath jsonPath = given().queryParam("email", email)
                .and()
                .queryParam("password", password)
                .and()
                .when()
                .get(ConfigurationReader.getProperty("base_uri") + "/sign")
                .then()
                .statusCode(200)
                .and()
                .extract().jsonPath();

        String accessToken = jsonPath.getString("accessToken");

        String finalToken = "Bearer " + accessToken;

        return finalToken;

    }




}

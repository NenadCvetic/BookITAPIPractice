package com.cydeo.practice;

import com.cydeo.pojos.Location;
import com.cydeo.pojos.Room;
import com.cydeo.utilities.BookITTestBase;
import com.cydeo.utilities.BookITUtils;
import com.cydeo.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

        Room as = given().accept(ContentType.JSON)
                .and()
                .headers("Authorization", BookITUtils.getTokenByRole("team_leader"))
                .and()
                .when()
                .get("/api/campuses")
                .then()
                .statusCode(200)
                .and()
                .log().all()
                .extract().as(Room.class);




    }


    @Test
    public void test4 () {


        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and()
                .headers("Authorization", BookITUtils.getTokenByRole("team_leader"))
                .and()
                .when()
                .get("/api/campuses")
                .then()
                .statusCode(200)
                .and()
                .log().all()
                .extract().jsonPath();


        List<Room> list = jsonPath.getList("[0].clusters[0].rooms", Room.class);

        for (Room each : list) {

            if (each.getId() == 113) {

                Assertions.assertTrue(each.getName().equals("yale"));

                System.out.println(each.getName());
            }

        }

        Location location1 = jsonPath.getObject("[1]", Location.class);

        Assertions.assertEquals(11233,location1.getId());

        System.out.println(location1.getId());


        List<Location> list1 = jsonPath.getList("", Location.class);

        System.out.println(list1);


    }





}

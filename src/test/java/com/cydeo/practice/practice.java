package com.cydeo.practice;

import com.cydeo.pojos.Location;
import com.cydeo.pojos.Room;
import com.cydeo.pojos.Student;
import com.cydeo.utilities.BookITTestBase;
import com.cydeo.utilities.BookITUtils;
import com.cydeo.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

import static org.hamcrest.MatcherAssert.*;

import static org.hamcrest.Matchers.*;

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


    @DisplayName("POST request Conferences")
    @Test
    public void test5 () {

        Map<String, Object> postRequestMap = new LinkedHashMap<>();

        postRequestMap.put("conference-type", "solid");
        postRequestMap.put("room-id",115);
        postRequestMap.put("year", 2023);
        postRequestMap.put("month", 5);
        postRequestMap.put("day", 28);
        postRequestMap.put("timeline-id",11243);
        postRequestMap.put("notify-team", false);


        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .headers("Authorization", BookITUtils.getTokenByRole("teacher"))
                .queryParams(postRequestMap)
                .log().all()
                .and()
                .when()
                .post("/api/conferences/conference")
                .then()
                .statusCode(200)
                .log().all().extract().jsonPath();

        int conference_id = jsonPath.getInt("id");

        int room_id = jsonPath.getInt("room.id");

        String firstName = jsonPath.getString("reservator.firstName");

        int timelineID = jsonPath.getInt("timelines[0].id");

        int timelineHour = jsonPath.getInt("timelines[0].start.hour");


        Response response = given().accept(ContentType.JSON)
                .and()
                .headers("Authorization", BookITUtils.getTokenByRole("teacher"))
                .pathParam("id", conference_id)
                .and()
                .get("/api/conferences/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        assertThat(response.path("type"),is(equalToIgnoringCase((String) postRequestMap.get("conference-type"))));

        assertThat(response.path("room.id"),is(equalTo(postRequestMap.get("room-id"))));

        assertThat(response.path("timelines[0].id"),is(equalTo(postRequestMap.get("timeline-id"))));






    }


    @Test
    public void test7 () {


        Map<String, Object> requestMap = new LinkedHashMap<>();

        requestMap.put("first-name","Petar");
        requestMap.put("last-name","Petrovic");
        requestMap.put("email","petarpetrovic92@gmail.com");
        requestMap.put("password","petar2222");
        requestMap.put("role","student-team-member");
        requestMap.put("campus-location","VA");
        requestMap.put("batch-number",20);
        requestMap.put("team-name","TheyBite");


        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .headers("Authorization", BookITUtils.getTokenByRole("teacher"))
                .queryParams(requestMap)
                .log().all()
                .and()
                .when()
                .post("/api/students/student")
                .then()
                .statusCode(201)
                .log().all().extract().jsonPath();

        int entryiId = jsonPath.getInt("entryiId");


        given().accept(ContentType.JSON)
                .and()
                .headers("Authorization",BookITUtils.getTokenByRole("teacher"))
                .and()
                .pathParam("id",entryiId)
                .when()
                .get("/api/students/{id}")
                .then()
                .statusCode(200)
                .log().all();

        given().accept(ContentType.JSON)
                .and()
                .headers("Authorization",BookITUtils.getTokenByRole("teacher"))
                .and()
                .pathParam("id",entryiId)
                .and()
                .delete("/api/students/{id}")
                .then()
                .statusCode(204)
                .log().all();



    }
    @Test
    public void test8 (){
       Map<String,Object> putMap= new LinkedHashMap<>();
       putMap.put("first-name","Nenad");
       putMap.put("last-name","Volkan");
       putMap.put("email","nenadvolkan@gmail.com");
       putMap.put("password",654321);
       putMap.put("role","student-team-member");

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .headers("Authorization", BookITUtils.getTokenByRole("teacher"))
                .and()
                .pathParam("id", 17131)
                .and()
                .queryParams(putMap)
                .when()
                .put("/api/students/{id}")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

           int id= response.path("id");
           System.out.println("id = " + id);



    }





}

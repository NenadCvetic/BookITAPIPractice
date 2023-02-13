package com.cydeo.utilities;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.BeforeAll;

public class BookITTestBase {


    @BeforeAll
    public static void setUp () {

        baseURI = "https://api.qa.bookit.cydeo.com";



    }





}

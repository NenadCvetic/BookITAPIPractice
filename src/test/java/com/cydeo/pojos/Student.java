package com.cydeo.pojos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = "batch_number_EU",ignoreUnknown = true)
public class Student {


    @JsonProperty("first-name")
    private String first_name;
    @JsonProperty("last-name")
    private String last_name;
    private String email;
    private String password;
    private String role;
    @JsonProperty("campus-location")
    private String campus_location;
    @JsonProperty("batch-number")
    private int batch_number;
    @JsonProperty("team-name")
    private String team_name;
    private String batch_number_EU;



}

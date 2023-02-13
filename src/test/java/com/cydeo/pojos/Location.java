package com.cydeo.pojos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Location {

    private int id;
    private String location;
    private List<Cluster> clusters;



}

package com.cydeo.pojos;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Room {

        private int id;
        private String name;
        private String description;
        private int capacity;
        private boolean withTV;
        private boolean withWhiteBoard;

}

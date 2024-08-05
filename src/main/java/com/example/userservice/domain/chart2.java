package com.example.userservice.domain;

import lombok.Data;

@Data
public class chart2 {
    String name;
     Double[]data;
    public chart2(String name,Double[]data) {
        this.name=name;
        this.data=data;
    }

}

package com.example.userservice.domain;

import lombok.Data;

@Data
public class chart {
    String name;
    Double y;

public chart(String name,Double y) {
    this.name=name;
    this.y=y;
}}

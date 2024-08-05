package com.example.userservice.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/file")
public class Upload_file {
    public static final String directory =(System.getProperty("user.home") + "/Téléchargements/");

}

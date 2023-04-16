package org.iclass.story;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class StoryBoardApplication {
    public static void main(String[] args) {

        SpringApplication.run(StoryBoardApplication.class, args);
    }

}
// C:\Users\myste>netstat -a -o  또는 netstat -ano | findstr 8080
// C:\Users\myste>taskkill /f /pid 29556

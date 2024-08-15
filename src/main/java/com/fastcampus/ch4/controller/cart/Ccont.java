package com.fastcampus.ch4.controller.cart;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/cccc"))
public class Ccont {

    @PostMapping("/count")
//    public ResponseEntity countUser(@RequestBody User user) {
    public ResponseEntity countUser(User user) {

        String message = user.getName() + "안녕하시오!";
        ResponseEntity responseEntity = new ResponseEntity(message, HttpStatus.OK);
        return responseEntity;
    }

    static class User {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

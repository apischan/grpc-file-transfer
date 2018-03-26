package com.apischan.filetransfer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileTransferController {

    @RequestMapping("/")
    public String transfer() {
        return "Greetings from Spring Boot!";
    }

}

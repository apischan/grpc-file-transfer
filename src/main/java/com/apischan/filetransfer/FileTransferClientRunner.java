package com.apischan.filetransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan("com.apischan.filetransfer")
public class FileTransferClientRunner {
    public static void main(String[] args) {
        SpringApplication.run(FileTransferClientRunner.class, args);
    }
}

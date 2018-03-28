package com.apischan.filetransfer;

import com.apischan.filetransfer.config.FileTransferConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileTransferClientRunner {
    public static void main(String[] args) {
        SpringApplication.run(FileTransferConfig.class, args);
    }
}

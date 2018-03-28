package com.apischan.filetransfer.controller;

import com.apischan.file.DownloadRequest;
import com.apischan.filetransfer.model.DownloadFileRequest;
import com.apischan.filetransfer.service.FileTransferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
public class FileTransferController {

    private final FileTransferService fileTransferService;

    @PostMapping("/")
    public Mono<Void> transfer(@RequestBody DownloadFileRequest downloadFileRequest) {
        log.info("File name...: {}", downloadFileRequest.getFileName());
        String filePath = downloadFileRequest.getServerFilePath() + "/" + downloadFileRequest.getClientFilePath();
        DownloadRequest request = DownloadRequest.newBuilder()
                .setFilePath(filePath)
                .build();
        return fileTransferService.downloadFile(request)
                .flatMap(content -> fileTransferService.saveFile(content, downloadFileRequest.getClientFilePath()));

    }

}

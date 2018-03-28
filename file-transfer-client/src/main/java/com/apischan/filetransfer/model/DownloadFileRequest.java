package com.apischan.filetransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DownloadFileRequest {
    private String fileName;
    private String serverFilePath;
    private String clientFilePath;
}

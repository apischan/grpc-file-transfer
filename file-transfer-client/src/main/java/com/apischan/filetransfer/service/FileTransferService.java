package com.apischan.filetransfer.service;

import com.apischan.file.DownloadRequest;
import com.apischan.file.FileBatch;
import com.apischan.file.FileDownloadServiceGrpc;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Service
public class FileTransferService {

    private FileDownloadServiceGrpc.FileDownloadServiceStub stub;

    public Mono<ByteString> downloadFile(DownloadRequest downloadFileRequest) {
        return Mono.create(sink -> {
            stub.download(downloadFileRequest, new StreamObserver<FileBatch>() {
                private List<ByteString> chunks = new LinkedList<>();

                @Override
                public void onNext(FileBatch value) {
                    chunks.add(value.getContent());
                }

                @Override
                public void onError(Throwable t) {
                    sink.error(t);
                }

                @Override
                public void onCompleted() {
                    ByteString initial = ByteString.EMPTY;
                    ByteString content = chunks.stream()
                            .reduce(initial, ByteString::concat);
                    sink.success(content);
                }
            });
        });
    }

    @SneakyThrows
    public Mono<Void> saveFile(ByteString content, String path) {
        return Mono.defer(() -> {
            File file = new File(path);
            FileUtils.forceMkdirParent(file);
            Files.createDirectories(file.toPath());
            Files.createFile(file.toPath());
            FileOutputStream fos = new FileOutputStream(file);
            content.writeTo(fos);
            return Mono.empty();
        });
    }

}

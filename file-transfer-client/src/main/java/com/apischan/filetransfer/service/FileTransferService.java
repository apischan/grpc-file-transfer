package com.apischan.filetransfer.service;

import com.apischan.file.DownloadRequest;
import com.apischan.file.FileBatch;
import com.apischan.file.FileDownloadServiceGrpc;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileOutputStream;
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

    public Mono<Void> saveFile(ByteString content, String path, String fileName) {
        return Mono.fromRunnable(() -> saveFileSync(content, path, fileName));
    }

    @SneakyThrows
    private void saveFileSync(ByteString content, String path, String fileName) {
        File file = new File(path, fileName);
        boolean isCreated = file.getParentFile().mkdirs();
        if (isCreated) {
            FileOutputStream fos = new FileOutputStream(file);
            content.writeTo(fos);
        }
    }

}

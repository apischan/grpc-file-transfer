package com.apischan.filetransfer.config;

import com.apischan.file.FileDownloadServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
@ComponentScan("com.apischan.filetransfer")
public class FileTransferConfig {

    @Bean
    public ManagedChannel managedChannel() {
        return NettyChannelBuilder.forAddress("localhost", 8090)
                .usePlaintext(true)
                .build();
    }

    @Bean
    public FileDownloadServiceGrpc.FileDownloadServiceStub fileStreamingService(ManagedChannel managedChannel) {
        return  FileDownloadServiceGrpc.newStub(managedChannel);
    }

}

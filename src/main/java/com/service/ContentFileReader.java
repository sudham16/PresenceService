package com.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;



@Component
public class ContentFileReader {

    @Value("${queue.output.directory}")
    private String outputDir;

    public  String returnFileContent() throws IOException {
        return Files.readString(Path.of(outputDir));
    }



}

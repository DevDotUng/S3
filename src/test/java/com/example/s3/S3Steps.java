package com.example.s3;

import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

public class S3Steps {
    public static MockMultipartFile 이미지_생성() throws IOException {
        final String fileName = "testImage"; //파일명
        final String contentType = "png"; //파일타입
        final String filePath = "src/test/resources/testImage/"+fileName+"."+contentType; //파일경로
        FileInputStream fileInputStream = new FileInputStream(filePath);

        return new MockMultipartFile(
                "file",
                fileName + "." + contentType,
                contentType,
                fileInputStream
        );
    }
}

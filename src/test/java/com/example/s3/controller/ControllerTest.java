package com.example.s3.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.s3.ApiTest;
import com.example.s3.adapter.S3Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.s3.S3Steps.이미지_생성;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ControllerTest extends ApiTest {

    @Autowired
    private S3Repository s3Repository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AmazonS3Client mockAmazonS3Client;

    @BeforeEach
    void beforeEach() throws MalformedURLException {
        final String url = "https://s3-ung-sample.s3.ap-northeast-2.amazonaws.com/testImage.png";
        given(mockAmazonS3Client.putObject(any(PutObjectRequest.class))).willReturn(new PutObjectResult());
        given(mockAmazonS3Client.getUrl(any(), any())).willReturn(new URL(url));
    }

    @Test
    void 이미지_등록() throws Exception {
        final var file = 이미지_생성();

        mvc.perform(
                multipart("/s3/upload")
                        .file(file)
        ).andExpect(status().isOk());
    }

//    @Test
//    void 이미지_삭제() throws Exception {
//        final var file = 이미지_생성();
//
//        mvc.perform(
//                multipart("/s3/upload")
//                        .file(file)
//        ).andExpect(status().isOk());
//    }
}

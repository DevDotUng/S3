package com.example.s3.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.s3.ApiTest;
import com.example.s3.adapter.S3Repository;
import com.example.s3.domain.S3Image;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.example.s3.S3Steps.이미지_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ControllerTest extends ApiTest {

    final String imageUrl = "https://s3-ung-sample/test/testImage.png";

    final String imageName = "testImage.png";

    @Autowired
    private S3Repository s3Repository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @BeforeEach
    void beforeEach() {
        given(amazonS3Client.putObject(any(PutObjectRequest.class))).willReturn(new PutObjectResult());
        given(amazonS3Client.doesObjectExist(any(), any())).willReturn(true);
        doNothing().when(amazonS3Client).deleteObject(any(), any());
    }

    @Test
    void 이미지_등록() throws Exception {
        final var file = 이미지_생성();

        mvc.perform(
                multipart("/s3/upload")
                        .file(file)
        ).andExpect(status().isOk());
    }

    @Test
    void 이미지_삭제() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/s3/delete")
                        .param("image", imageName)
        ).andExpect(status().isOk());
    }

    @Test
    void 이미지_조회() throws Exception {
        final var file = 이미지_생성();

        mvc.perform(
                multipart("/s3/upload")
                        .file(file)
        );

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/s3/image"))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();

        ObjectMapper mapper = new ObjectMapper();

        List<S3Image> after = mapper.readValue(
                result.getResponse().getContentAsString()
                , new TypeReference<>(){});

        assertThat(after.get(0).getImage()).isEqualTo(imageUrl);
    }
}

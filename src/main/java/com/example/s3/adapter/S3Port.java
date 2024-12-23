package com.example.s3.adapter;

import com.example.s3.domain.S3Image;

import java.util.List;

public interface S3Port {
    void save(S3Image s3Image);
    List<S3Image> getImage();
}

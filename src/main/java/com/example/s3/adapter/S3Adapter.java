package com.example.s3.adapter;

import com.example.s3.domain.S3Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class S3Adapter implements S3Port {

    @Autowired
    private S3Repository s3Repository;

    @Override
    public void save(S3Image s3Image) {
        s3Repository.save(s3Image);
    }

    @Override
    public List<S3Image> getImage() {
        return s3Repository.findAll();
    }
}

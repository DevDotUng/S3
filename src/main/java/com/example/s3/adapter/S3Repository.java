package com.example.s3.adapter;

import com.example.s3.domain.S3Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S3Repository extends JpaRepository<S3Image, Long> {}
package com.example.flink;

import com.example.flink.job.KafkaFlinkJob;
import com.example.flink.job.SimpleFlinkJob;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlinkApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(FlinkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 启动Flink作业
        KafkaFlinkJob.runFlinkJob();
    }
}
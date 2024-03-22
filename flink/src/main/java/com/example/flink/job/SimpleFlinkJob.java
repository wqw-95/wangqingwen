package com.example.flink.job;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
public class SimpleFlinkJob {
    public static void runFlinkJob() throws Exception {
        // 设置执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 创建数据源
        DataStream<String> text = env.fromElements("Hello", "Flink", "World");

        // 对数据源进行操作
        text.map(String::toUpperCase).print();

        // 执行作业
        env.execute("Simple Flink Job");

    }
}

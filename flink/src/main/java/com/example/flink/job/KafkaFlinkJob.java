package com.example.flink.job;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class KafkaFlinkJob {

    public static void runFlinkJob() throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 配置连接到Kafka的属性
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "10.254.131.147:9092"); // Kafka集群地址
        properties.setProperty("group.id", "device_match_list_group"); // 消费者组
        // 创建Kafka数据源
        DataStream<String> stream = env.addSource(new FlinkKafkaConsumer<>(
                "DWD_DEVICE_NEW_SIDE_OUTPUT", // Kafka主题
                new SimpleStringSchema(), // 序列化模式
                properties));
        // 现在可以对stream进行操作了
        stream.map(String::toUpperCase).print();
        // 执行作业
        env.execute("Kafka Flink Job");
    }
}


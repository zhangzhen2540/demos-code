package org.example.demo.rabbitmq;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 网络接口 eth0(lo) 上添加一个延迟规则，平均延迟是 100ms（带宽是 50ms 的随机波动）
 *  sudo tc qdisc add dev lo root netem delay 100ms 50ms
 *
 * 要移除之前添加的网络堵塞规则，使用以下命令
 *  sudo tc qdisc del dev lo root
 */
public class App {
    static String queue = "my-queue";
    static String exchange = "my-exchange";
    static String bindingKey = "my-binding-key";

    @SneakyThrows
    public static void main(String[] args) {

        // ceshi
        Connection connection = RabbitmqConnUtil.getConnection();

        sendAndClose(connection);
//        useAloneThread(connection);

        connection.close();
    }

    @SneakyThrows
    private static void sendAndClose(Connection connection) {
        Channel channel = connection.createChannel();
        channel.confirmSelect();
        channel.queueDeclare(queue, true, false, false, Map.of());
        channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC, true, false, Map.of());
        channel.queueBind(queue, exchange, bindingKey);

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(">>>>>>>>>> confirm ack " + deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(">>>>>>>>>> confirm nack " + deliveryTag);
            }
        });

        channel.basicPublish(exchange, bindingKey, new AMQP.BasicProperties.Builder().contentEncoding("UTF-8").build(),
                "hello world".getBytes());
        channel.close();
    }

    @SneakyThrows
    private static void useAloneThread(Connection connection) {
        CountDownLatch cd = new CountDownLatch(1);
        Thread thread = new Thread(() -> {
            try {
                Channel channel = connection.createChannel();
                channel.confirmSelect();
                channel.queueDeclare(queue, true, false, false, Map.of());
                channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC, true, false, Map.of());
                channel.queueBind(queue, exchange, bindingKey);

                channel.addConfirmListener(new ConfirmListener() {
                    @Override
                    public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                        System.out.println(">>>>>>>>>> confirm ack " + deliveryTag);
                    }

                    @Override
                    public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                        System.out.println(">>>>>>>>>> confirm nack " + deliveryTag);
                    }
                });

                channel.basicPublish(exchange, bindingKey, new AMQP.BasicProperties.Builder().contentEncoding("UTF-8").build(),
                        "hello world".getBytes());
                cd.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        cd.await();
        try {
            thread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        channel.close();
//        channel.abort();

//        Thread.sleep(100);

    }
}

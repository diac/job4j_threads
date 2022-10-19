package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void whenPollFromOverflow() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread consumer = new Thread(() -> {
            try {
                queue.poll();
                queue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread producer = new Thread(() -> {
            try {
                queue.offer(1);
                queue.offer(2);
                queue.offer(3);
                queue.offer(4);
                queue.offer(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(List.of(queue.poll(), queue.poll(), queue.poll())).containsExactly(3, 4, 5);
    }
}
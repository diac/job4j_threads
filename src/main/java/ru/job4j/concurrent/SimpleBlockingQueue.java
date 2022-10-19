package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final int capacity;

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= capacity) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        notifyAll();
        return result;
    }
}

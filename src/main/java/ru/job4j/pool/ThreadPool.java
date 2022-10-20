package ru.job4j.pool;

import ru.job4j.concurrent.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private static final int THREADS_NUMBER = Runtime.getRuntime().availableProcessors();
    private static final int TASKS_NUMBER = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(TASKS_NUMBER);

    public ThreadPool() {
        for (var i = 0; i < THREADS_NUMBER; i++) {
            threads.add(new Thread(() -> {
                try {
                    while (tasks.isEmpty()) {
                        wait();
                    }
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        notifyAll();
    }

    public void shutdown() throws InterruptedException {
        while (!tasks.isEmpty()) {
            tasks.poll();
        }
        threads.forEach(thread -> Thread.currentThread().interrupt());
    }
}

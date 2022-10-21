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
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(thread -> Thread.currentThread().interrupt());
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        pool.work(() -> System.out.println("Hello"));
        pool.work(() -> System.out.println("Howdy"));
        pool.shutdown();
    }
}
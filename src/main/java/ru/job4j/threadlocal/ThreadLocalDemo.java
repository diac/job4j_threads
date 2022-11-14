package ru.job4j.threadlocal;

public class ThreadLocalDemo {

    private static ThreadLocal<String> tl = new ThreadLocal<>();

    public static ThreadLocal<String> getTl() {
        return tl;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread first = new FirstThread();
        Thread second = new SecondThread();
        tl.set("Это поток main.");
        System.out.println(tl.get());
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
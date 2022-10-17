package ru.job4j.concurrent;

public class ThreadState {

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName() + ": " + Thread.currentThread().getState())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName() + ": " + Thread.currentThread().getState())
        );
        System.out.println("First state: " + first.getState());
        System.out.println("Second state: " + second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("First state: " + first.getState());
            System.out.println("Second state: " + second.getState());
        }
        System.out.println("First state: " + first.getState());
        System.out.println("Second state: " + second.getState());
        System.out.println();
        System.out.println("Работа завершена");
    }
}

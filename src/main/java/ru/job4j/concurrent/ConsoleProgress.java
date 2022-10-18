package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    private static final char[] SPINNER = new char[] {'-', '\\', '|', '/'};

    @Override
    public void run() {
        byte i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if (i == SPINNER.length) {
                i = 0;
            }
            System.out.print("\r load: " + SPINNER[i++]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
        try {
            progress.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(getOutputFileName(url))) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, speed)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validateArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    private static void validateArgs(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Необходимо указать два аргумента для запуска");
        }
        if (!isValidUrl(args[0])) {
            throw new IllegalArgumentException(String.format("Неверный URL: %s", args[0]));
        }
        if (!isInteger(args[1])) {
            throw new IllegalArgumentException(String.format("Неверное значение параметра speed: %s", args[1]));
        }
        int speed = Integer.parseInt(args[1]);
        if (speed <= 0) {
            throw new IllegalArgumentException(String.format("Значение параметра speed должно быть больше нуля. Указанное значение: %s", args[1]));
        }
    }

    private static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isInteger(String integerStr) {
        try {
            Integer.parseInt(integerStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String getOutputFileName(String sourceUrl) {
        return "download_" + sourceUrl.substring(sourceUrl.lastIndexOf('/') + 1);
    }
}

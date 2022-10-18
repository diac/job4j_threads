package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import static ru.job4j.concurrent.WgetArgsValidator.validateArgs;

public class Wget implements Runnable {

    private static final int SECOND = 1000;

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (
                BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(getOutputFileName(url))
        ) {
            long startTime = System.currentTimeMillis();
            long timePerStep;
            long pauseMillis;
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            int downloadedBytes = 0;
            while ((bytesRead = in.read(dataBuffer, 0, speed)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadedBytes += bytesRead;
                if (downloadedBytes >= speed) {
                    timePerStep = System.currentTimeMillis() - startTime;
                    if (timePerStep < SECOND) {
                        pauseMillis = SECOND - timePerStep;
                        try {
                            Thread.sleep(pauseMillis);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    startTime = System.currentTimeMillis();
                    downloadedBytes = 0;
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

    private static String getOutputFileName(String sourceUrl) {
        return "download_" + sourceUrl.substring(sourceUrl.lastIndexOf('/') + 1);
    }
}

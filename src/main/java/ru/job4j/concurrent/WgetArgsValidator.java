package ru.job4j.concurrent;

import java.net.URL;

public final class WgetArgsValidator {

    private WgetArgsValidator() {
    }

    public static void validateArgs(String[] args) {
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

    public static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isInteger(String integerStr) {
        try {
            Integer.parseInt(integerStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

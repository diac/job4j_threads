package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() throws IOException {
        return filterContent(character -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return filterContent(character -> character < 0x80);
    }

    private synchronized String filterContent(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > -1) {
                if (filter.test((char) data)) {
                    output.append(data);
                }
            }
        }
        return output.toString();
    }
}

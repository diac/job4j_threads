package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int currentCount;
        do {
            currentCount = count.get();
        } while (!count.compareAndSet(currentCount, ++currentCount));
    }

    public int get() {
        return count.get();
    }
}

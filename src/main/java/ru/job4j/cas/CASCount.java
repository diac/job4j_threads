package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int currentCount;
        int newCount;
        do {
            currentCount = count.get();
            newCount = currentCount + 1;
        } while (!count.compareAndSet(currentCount, newCount));
    }

    public int get() {
        return count.get();
    }
}

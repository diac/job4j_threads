package ru.job4j.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    public void whenAdd() throws InterruptedException {
        Cache cache = new Cache();
        Thread first = new Thread(() -> cache.add(new Base(1, 1)));
        Thread second = new Thread(() -> cache.add(new Base(1, 2)));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(cache.get(1)).isEqualTo(new Base(1, 1));
    }

    @Test
    public void whenUpdateThenSuccess() throws InterruptedException {
        Cache cache = new Cache();
        Thread first = new Thread(() -> {
            Base original = new Base(1, 1);
            original.setName("Original");
            cache.add(original);
        });
        Thread second = new Thread(() -> {
            Base updated = new Base(1, 1);
            updated.setName("Updated");
            cache.update(updated);
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(cache.get(1).getName()).isEqualTo("Updated");
    }

    @Test
    public void whenUpdateThenThrowException() throws InterruptedException {
        Cache cache = new Cache();
        Thread first = new Thread(() -> {
            Base original = new Base(1, 1);
            original.setName("Original");
            cache.add(original);
        });
        Thread second = new Thread(() -> {
            Base updated = new Base(1, 2);
            updated.setName("Updated");
            Assertions.assertThrows(
                    OptimisticException.class,
                    () -> cache.update(updated)
            );
        });
        first.start();
        second.start();
        first.join();
        second.join();
    }

    @Test
    public void whenDelete() throws InterruptedException {
        Cache cache = new Cache();
        Thread first = new Thread(() -> {
            Base original = new Base(1, 1);
            cache.add(original);
        });
        Thread second = new Thread(() -> cache.delete(new Base(1, 1)));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(cache.get(1)).isNull();
    }
}
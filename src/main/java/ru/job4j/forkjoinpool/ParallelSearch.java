package ru.job4j.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static java.lang.Math.max;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private static final int BLOCK_SIZE = 10;

    private final T needle;

    private final T[] haystack;
    private final int from;
    private final int to;

    public ParallelSearch(T needle, T[] haystack, int from, int to) {
        this.needle = needle;
        this.haystack = haystack;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from < BLOCK_SIZE) {
            return simpleSearch(from, to);
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(needle, haystack, from, mid);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(needle, haystack, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        return max(leftSearch.join(), rightSearch.join());
    }

    public static <T> int search(T needle, T[] haystack) {
        int result;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        result = forkJoinPool.invoke(new ParallelSearch<>(needle, haystack, 0, haystack.length - 1));
        return result;
    }

    private int simpleSearch(int from, int to) {
        var result = -1;
        for (var index = from; index <= to; index++) {
            if (needle.equals(haystack[index])) {
                result = index;
                break;
            }
        }
        return result;
    }
}

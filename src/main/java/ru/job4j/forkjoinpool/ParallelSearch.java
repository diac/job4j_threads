package ru.job4j.forkjoinpool;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private static final int BLOCK_SIZE = 10;

    private final T needle;

    private final List<T> haystack;
    private final int from;
    private final int to;

    public ParallelSearch(T needle, List<T> haystack) {
        this.needle = needle;
        this.haystack = haystack;
        this.from = 0;
        this.to = haystack.size() - 1;
    }

    public ParallelSearch(T needle, List<T> haystack, int from, int to) {
        this.needle = needle;
        this.haystack = haystack;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (from == to) {
            return simpleSearch(from, to);
        }
        int result;
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(needle, haystack, from, mid);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(needle, haystack, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        if (left != -1) {
            result = left;
        } else if (right != -1) {
            result = right;
        } else {
            result = simpleSearch(from, to);
        }
        return result;
    }

    public int search() {
        int result;
        if (haystack.size() > BLOCK_SIZE) {
            ForkJoinPool forkJoinPool = new ForkJoinPool();
            result = forkJoinPool.invoke(new ParallelSearch<>(needle, haystack, 0, haystack.size() - 1));
        } else {
            result = simpleSearch(0, haystack.size());
        }
        return result;
    }

    private int simpleSearch(int from, int to) {
        var result = -1;
        for (var index = from; index < to; index++) {
            if (needle.equals(haystack.get(index))) {
                result = index;
                break;
            }
        }
        return result;
    }
}

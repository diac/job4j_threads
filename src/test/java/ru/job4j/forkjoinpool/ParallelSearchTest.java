package ru.job4j.forkjoinpool;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelSearchTest {

    @Test
    public void whenSearchShortListOfString() {
        List<String> array = List.of(
                "One",
                "Two",
                "Three",
                "Four",
                "Five"
        );
        ParallelSearch<String> search = new ParallelSearch<>("Two", array);
        int expected = search.search();
        assertThat(expected).isEqualTo(1);
    }

    @Test
    public void whenSearchLongListOfString() {
        List<String> array = List.of(
                "One",
                "Two",
                "Three",
                "Four",
                "Five",
                "Six",
                "Seven",
                "Eight",
                "Nine",
                "Ten",
                "Eleven",
                "Twelve",
                "Thirteen",
                "Fourteen"
        );
        ParallelSearch<String> search = new ParallelSearch<>("Thirteen", array);
        int expected = search.search();
        assertThat(expected).isEqualTo(12);
    }

    @Test
    public void whenSearchShortListOfInteger() {
        List<Integer> array = List.of(1, 2, 3, 4, 5);
        ParallelSearch<Integer> search = new ParallelSearch<>(3, array);
        int expected = search.search();
        assertThat(expected).isEqualTo(2);
    }

    @Test
    public void whenSearchLongListOfInteger() {
        List<Integer> array = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
        ParallelSearch<Integer> search = new ParallelSearch<>(15, array);
        int expected = search.search();
        assertThat(expected).isEqualTo(14);
    }

    @Test
    public void whenSearchNotFound() {
        List<Integer> array = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
        ParallelSearch<Integer> search = new ParallelSearch<>(1000, array);
        int expected = search.search();
        assertThat(expected).isEqualTo(-1);
    }
}
package ru.job4j.forkjoinpool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelSearchTest {

    @Test
    public void whenSearchShortListOfString() {
        String[] array = new String[]{
                "One",
                "Two",
                "Three",
                "Four",
                "Five"
        };
        int expected = ParallelSearch.search("Two", array);
        assertThat(expected).isEqualTo(1);
    }

    @Test
    public void whenSearchLongListOfString() {
        String[] array = new String[]{
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
        };
        int expected = ParallelSearch.search("Thirteen", array);
        assertThat(expected).isEqualTo(12);
    }

    @Test
    public void whenSearchShortListOfInteger() {
        Integer[] array = new Integer[] {1, 2, 3, 4, 5};
        int expected = ParallelSearch.search(3, array);
        assertThat(expected).isEqualTo(2);
    }

    @Test
    public void whenSearchLongListOfInteger() {
        Integer[] array = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
        int expected = ParallelSearch.search(15, array);
        assertThat(expected).isEqualTo(14);
    }

    @Test
    public void whenSearchNotFound() {
        Integer[] array = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
        int expected = ParallelSearch.search(1000, array);
        assertThat(expected).isEqualTo(-1);
    }

    @Test
    public void whenSearchLast() {
        Integer[] array = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
        int expected = ParallelSearch.search(21, array);
        assertThat(expected).isEqualTo(20);
    }
}
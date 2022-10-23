package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    public void whenSyncSum() {
        int[][] matrix = new int[][] {
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        };
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        assertThat(result[0].getColSum()).isEqualTo(3);
        assertThat(result[0].getRowSum()).isEqualTo(6);
        assertThat(result[1].getColSum()).isEqualTo(6);
        assertThat(result[1].getRowSum()).isEqualTo(6);
        assertThat(result[2].getColSum()).isEqualTo(9);
        assertThat(result[2].getRowSum()).isEqualTo(6);
    }

    @Test
    public void whenAsyncSum() throws InterruptedException, ExecutionException {
        int[][] matrix = new int[][] {
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        };
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        assertThat(result[0].getColSum()).isEqualTo(3);
        assertThat(result[0].getRowSum()).isEqualTo(6);
        assertThat(result[1].getColSum()).isEqualTo(6);
        assertThat(result[1].getRowSum()).isEqualTo(6);
        assertThat(result[2].getColSum()).isEqualTo(9);
        assertThat(result[2].getRowSum()).isEqualTo(6);
    }
}
package ru.job4j.pools;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        private void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        private void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (var i = 0; i < matrix.length; i++) {
            if (sums[i] == null) {
                sums[i] = new Sums();
            }
            for (var j = 0; j < matrix[i].length; j++) {
                sums[i].setColSum(sums[i].getColSum() + matrix[j][i]);
                sums[i].setRowSum(sums[i].getRowSum() + matrix[i][j]);
            }
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws InterruptedException, ExecutionException {
        List<CompletableFuture<Sums>> promises = new ArrayList<>(matrix.length);
        for (var rowColIndex = 0; rowColIndex < matrix.length; rowColIndex++) {
            var i = rowColIndex;
            CompletableFuture<Sums> sumPromise = CompletableFuture.supplyAsync(() -> {
                Sums rowColSum = new Sums();
                for (var j = 0; j < matrix[i].length; j++) {
                    rowColSum.setColSum(rowColSum.getColSum() + matrix[j][i]);
                    rowColSum.setRowSum(rowColSum.getRowSum() + matrix[i][j]);
                }
                return rowColSum;
            });
            promises.add(sumPromise);
        }
        Sums[] sums = new Sums[matrix.length];
        for (var i = 0; i < matrix.length; i++) {
            sums[i] = promises.get(i).get();
        }
        return sums;
    }
}
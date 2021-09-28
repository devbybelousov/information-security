package ru.tusur.matrix;

import Jama.Matrix;

public class MatrixKey {

    private final double[][] matrix;
    private final int size;
    private final int matrixSize;

    public MatrixKey(double[][] matrix, int size) {
        this.matrix = matrix;
        this.size = size;
        this.matrixSize = size / 2;
    }

    public double[][] reverseKey() {
        double det = getDet(this.matrix) % size;
        System.out.println("det = " + det);
        int reverseDet = getReverseDet((int) det);
        System.out.println("reverse det = " + reverseDet);

        double[][] inverseMatrix = inverse(this.matrix);
        double[][] newMatrix = multiplication(division(inverseMatrix), reverseDet);
        double[][] transposeMatrix = transposeMatrix(newMatrix);

        return plus(transposeMatrix);
    }

    private int getReverseDet(int det) {
        int reverseDet = 1;

        while ((det * reverseDet) % size != 1) {
            reverseDet++;
            System.out.println(reverseDet + " " + (det * reverseDet) % size);
        }

        return reverseDet;
    }

    private double[][] multiplication(double[][] matrix, int reverseDet) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] *= reverseDet;
            }
        }
        return division(matrix);
    }

    private double[][] division(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = matrix[i][j] % size;
            }
        }
        return matrix;
    }

    private double[][] inverse(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                int sum = i + j + 2;
                double[][] minor = minor(matrix, i, j);
                double result = sum * getDet(minor);
                matrix[i][j] = result;
            }
        }
        return matrix;
    }

    private double[][] minor(double[][] matrix, int rowNum, int colNum) {
        final int n = matrix.length - 1;

        double[][] result = new double[n][n];

        for (int i = 0; i < matrix.length; i++) {
            boolean isRowDeleted = rowNum < i;
            int resultRowIndex = isRowDeleted ? i - 1 : i;

            for (int j = 0; j < matrix[i].length; j++) {
                boolean isColDeleted = colNum < j;
                int resultColIndex = isColDeleted ? j - 1 : j;

                if (rowNum != i && colNum != j)
                    result[resultRowIndex][resultColIndex] = matrix[i][j];
            }
        }
        return result;
    }

    private double getDet(double[][] array) {
        Matrix matrix = Matrix.constructWithCopy(array);
        return matrix.det();
    }

    private double[][] transposeMatrix(double[][] matrix) {
        Matrix newMatrix = Matrix.constructWithCopy(matrix);
        return newMatrix.transpose().getArray();
    }

    private double[][] plus(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] < 0) {
                    matrix[i][j] += size;
                }
            }
        }
        return matrix;
    }
}

package ru.tusur.matrix;

public class Matrix {

    private final double[][] data;
    private final int rows;
    private final int cols;

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(double[][] data) {
        this.data = data.clone();
        rows = this.data.length;
        cols = this.data[0].length;
    }

    public static Matrix subMatrix(Matrix matrix, int exclude_row, int exclude_col) {
        Matrix result = new Matrix(matrix.rows - 1, matrix.cols - 1);

        for (int row = 0, p = 0; row < matrix.rows; ++row) {
            if (row != exclude_row - 1) {
                for (int col = 0, q = 0; col < matrix.cols; ++col) {
                    if (col != exclude_col - 1) {
                        result.data[p][q] = matrix.data[row][col];

                        ++q;
                    }
                }
                ++p;
            }
        }

        return result;
    }

    public double determinant() {
        if (rows != cols) {
            return Double.NaN;
        } else {
            return _determinant(this);
        }
    }

    private double _determinant(Matrix matrix) {
        if (matrix.cols == 1) {
            return matrix.data[0][0];
        } else if (matrix.cols == 2) {
            return (matrix.data[0][0] * matrix.data[1][1] -
                    matrix.data[0][1] * matrix.data[1][0]);
        } else {
            double result = 0.0;

            for (int col = 0; col < matrix.cols; ++col) {
                Matrix sub = subMatrix(matrix, 1, col + 1);

                result += (Math.pow(-1, 1 + col + 1) *
                        matrix.data[0][col] * _determinant(sub));
            }

            return result;
        }
    }

    public double[][] invertMatrix(int size) {
        double invDet = modInverse(determinant(), size);
        return scalarMatrixMult(adjoint(data), (int) invDet, size);

    }

    private double[][] scalarMatrixMult(double[][] matrix, int x, int mod) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = mod((matrix[i][j] * x), mod);
            }
        }
        return matrix;
    }

    private double[][] adjoint(double[][] matrix) {
        double[][] newMatrix;
        double[][] adj = new double[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                newMatrix = removeRowCol(matrix, c, r);
                adj[r][c] += (int) Math.pow(-1, r + c) * _determinant(new Matrix(newMatrix));
            }
        }
        return adj;
    }

    private double[][] removeRowCol(double[][] matrix, int row, int col) {
        int oldRowLength = matrix.length;
        int oldColLength = matrix[0].length;
        double[][] newMatrix = new double[oldRowLength - 1][oldColLength - 1];

        int newR = 0;
        int newC;

        for (int r = 0; newR < oldRowLength - 1; r++, newR++) {
            if (r == row) r++;
            newC = 0;

            for (int c = 0; newC < oldColLength - 1; c++, newC++) {
                if (c == col) c++;
                newMatrix[newR][newC] = matrix[r][c];
            }
        }

        return newMatrix;
    }

    private double modInverse(double x, int mod) {
        for (int i = 0; i < mod; i++) {
            if (mod(x * i, mod) == 1)
                return i;
        }
        System.out.println("ERROR: NO INVERSE FOUND");
        return -1;
    }

    private double mod(double x, int mod) {
        if (x >= 0)
            return x % mod;
        else
            return ((x % mod) + mod);
    }
}

package ru.tusur.matrix;

import lombok.NoArgsConstructor;
import ru.tusur.model.CryptEncoder;
import ru.tusur.model.UniversalCryptEncoder;
import ru.tusur.service.ConsoleInput;
import ru.tusur.utils.Utils;

import java.util.Arrays;

@NoArgsConstructor
public class MatrixCryptEncoder extends UniversalCryptEncoder implements CryptEncoder {

    private final String DEFAULT_KEY = "Альпинизм";

    private String key = DEFAULT_KEY;
    private int sizeMatrix = (int) Math.sqrt(key.length());
    private int[][] matrix;

    public MatrixCryptEncoder(String key) {
        this.table = Utils.createArray(SIZE_TABLE);
        if (isRoot(key.length())) {
            this.key = key;
            this.sizeMatrix = (int) Math.sqrt(key.length());
        }

        matrix = new int[this.sizeMatrix][this.sizeMatrix];
        setMatrix();
    }

    private boolean isRoot(int num) {
        double root = Math.sqrt(num) * 10;
        return (root % 10) == 0;
    }

    private void setMatrix() {
        int[] arrayCodes = getArrayWord(key);

        int indexArray = 0;
        for (int i = 0; i < sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                matrix[i][j] = arrayCodes[indexArray];
                indexArray++;
            }
        }
    }

    @Override
    public CryptEncoder create() {
        ConsoleInput consoleInput = new ConsoleInput();
        String key = consoleInput.read("Введите ваш ключ (если ваш ключ не подойдет, будет использован по умолчанию):");
        return new MatrixCryptEncoder(key);
    }

    @Override
    public String encode(String var) {
        int[] arrayWord = getArrayWord(var);
        System.out.println(Arrays.toString(arrayWord));
        String encodeWord = getCalculateWord(arrayWord, convertMatrixToDouble());
        return getWordByIndexes(encodeWord);
    }

    @Override
    public String decode(String var) {
        MatrixKey matrixKey = new MatrixKey(convertMatrixToDouble(), SIZE_TABLE);
        double[][] inverseMatrix = matrixKey.reverseKey();
        int[] word = getArrayWord(var);

        String decodeWord = getCalculateWord(word, inverseMatrix);
        return getWordByIndexes(decodeWord);
    }

    private double[][] convertMatrixToDouble() {
        double[][] newMatrix = new double[sizeMatrix][sizeMatrix];
        for (int i = 0; i < sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        return newMatrix;
    }

    private String getCalculateWord(int[] word, double[][] matrix) {
        StringBuilder builder = new StringBuilder();
        int sizeBlock = (int) Math.ceil((double) word.length / sizeMatrix);

        for (int i = 0; i < sizeBlock; i++) {
            builder.append(calculateBlock(i, word, matrix));
        }
        return builder.toString();
    }

    private String calculateBlock(int index, int[] word, double[][] matrix) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < sizeMatrix; i++) {
            int sum = 0;
            for (int j = 0; j < sizeMatrix; j++) {
                int indexWord = index + j * sizeMatrix;
                int indexLetterWord = getIndexLetterWord(indexWord, word);
                int indexLetterKey = (int) matrix[i][j];
                sum += indexLetterWord * indexLetterKey;
            }
            int result = (int) ((double) sum % SIZE_TABLE);
            str.append(result).append(" ");
        }
        return str.toString();
    }

    private int getIndexLetterWord(int indexWord, int[] word) {
        if (indexWord >= word.length) {
            return 32;
        } else {
            return word[indexWord];
        }
    }
}

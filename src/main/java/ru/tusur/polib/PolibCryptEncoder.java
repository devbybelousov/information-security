package ru.tusur.polib;

import ru.tusur.model.CryptEncoder;
import ru.tusur.utils.Utils;

import static ru.tusur.utils.Utils.SIZE;

public class PolibCryptEncoder implements CryptEncoder {

    private final char[][] table;

    public PolibCryptEncoder() {
        table = Utils.createTable();
    }

    @Override
    public CryptEncoder create() {
        return new PolibCryptEncoder();
    }

    @Override
    public String encode(String var) {
        StringBuilder newWord = new StringBuilder();
        for (char c : var.toCharArray()) {
            newWord.append(getIndexByChar(c)).append(" ");
        }
        return newWord.toString();
    }

    private String getIndexByChar(char c) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE + 1; j++) {
                if (c == table[i][j]) {
                    return String.format("%d%d", i + 1, j + 1);
                }
            }
        }
        return "";
    }

    @Override
    public String decode(String var) {
        StringBuilder newWord = new StringBuilder();
        for (String symbol : var.split(" ")) {
            int firstIndex = Integer.parseInt(symbol.split("")[0]) - 1;
            int secondIndex = Integer.parseInt(symbol.split("")[1]) - 1;
            newWord.append(table[firstIndex][secondIndex]);
        }
        return newWord.toString();
    }
}

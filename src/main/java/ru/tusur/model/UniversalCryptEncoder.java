package ru.tusur.model;

public abstract class UniversalCryptEncoder {

    public final int SIZE_TABLE = 72;
    public char[] table;

    public int getIndexSymbol(char symbol) {
        for (int i = 0; i < SIZE_TABLE; i++) {
            if (table[i] == symbol) {
                return i;
            }
        }
        return 0;
    }

    public int[] getArrayWord(String word) {
        int[] arrayCodes = new int[word.length()];
        for (int i = 0; i < word.length(); i++) {
            arrayCodes[i] = getIndexSymbol(word.toCharArray()[i]);
        }
        return arrayCodes;
    }

    public String getWordByIndexes(String word) {
        StringBuilder decodeWord = new StringBuilder();
        for (String indexLetter : word.split(" ")) {
            decodeWord.append(table[Integer.parseInt(indexLetter)]);
        }
        return decodeWord.toString();
    }
}

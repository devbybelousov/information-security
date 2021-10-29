package ru.tusur.utils;

import ru.tusur.model.CryptEncoder;

import java.io.IOException;

public class Utils {

    public static final int SIZE = 8;
    private static final char[][] table = new char[SIZE][SIZE + 1];

    public static char[] createArray() {
        return getAlphabet().toCharArray();
    }

    public static char[][] createTable() {
        int indexSymbol = 0;
        String alphabet = getAlphabet();

        for (int row = 0; row < SIZE; row++) {
            for (int cell = 0; cell <= SIZE; cell++) {
                table[row][cell] = alphabet.toCharArray()[indexSymbol];
                indexSymbol++;
            }
        }
        return table;
    }

    private static String getAlphabet() {
        return "АБВГДЕЁЖЗИ" +
                "ЙКЛМНОПРС" +
                "ТУФХЦЧШЩЪ" +
                "ЫЬЭЮЯабвг" +
                "деёжзийклм" +
                "нопрстуфх" +
                "цчшщъыьэю" +
                "я !,.:?";
    }

    public static boolean isValid(String word) {
        return word.matches("[а-яА-Я !,.?:]*");
    }

    public static void run(String word, CryptEncoder cryptEncoder) {
        String encodeWord = cryptEncoder.encode(word);
        System.out.println("Зашифрованное слово: " + encodeWord);
        System.out.println("Расшифрованное слово: " + cryptEncoder.decode(encodeWord));

        waitAndClear();
    }

    private static void waitAndClear() {
        try {
            System.out.println("Нажмите [Enter] чтобы продолжить...");
            System.in.read();
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

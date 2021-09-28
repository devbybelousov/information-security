package ru.tusur.service;

import ru.tusur.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInput {

    private final BufferedReader reader;

    public ConsoleInput() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String read(String text) {
        System.out.println(text);
        String phrase = read();
        if (Utils.isValid(phrase)) {
            return phrase;
        } else {
            throw new IllegalArgumentException("Слово введено с недопустимыми символами!");
        }
    }

    private String read() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

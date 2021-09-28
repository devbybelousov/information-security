package ru.tusur.replace;

import lombok.NoArgsConstructor;
import ru.tusur.model.CryptEncoder;
import ru.tusur.model.UniversalCryptEncoder;
import ru.tusur.service.ConsoleInput;
import ru.tusur.utils.Utils;

@NoArgsConstructor
public class ReplaceCryptEncoder extends UniversalCryptEncoder implements CryptEncoder {

    private String key;

    public ReplaceCryptEncoder(String key) {
        this.key = key;
        table = Utils.createArray(SIZE_TABLE);
    }

    @Override
    public CryptEncoder create() {
        ConsoleInput input = new ConsoleInput();
        String key = input.read("Введите ваш ключ:");
        return new ReplaceCryptEncoder(key);
    }

    @Override
    public String encode(String var) {
        int indexKeySymbol = 0;
        StringBuilder word = new StringBuilder();

        for (char c : var.toCharArray()) {
            if (indexKeySymbol >= key.length()) {
                indexKeySymbol = 0;
            }

            int indexWord = getIndexSymbol(c);
            int indexKey = getIndexSymbol(key.toCharArray()[indexKeySymbol]);

            char letter = getLetter(indexWord, indexKey);
            word.append(letter);

            indexKeySymbol++;
        }
        return word.toString();
    }

    private char getLetter(int firstIndex, int lastIndex) {
        int sum = firstIndex + lastIndex;
        int indexLetter = (int) (sum % (double) SIZE_TABLE);
        return table[indexLetter];
    }

    @Override
    public String decode(String var) {
        StringBuilder word = new StringBuilder();
        int indexKeySymbol = 0;

        for (char c : var.toCharArray()) {
            if (indexKeySymbol >= key.length()) {
                indexKeySymbol = 0;
            }

            int indexWord = getIndexSymbol(c);
            int indexKey = getIndexSymbol(key.toCharArray()[indexKeySymbol]);

            word.append(getIndexEncodeSymbol(indexWord, indexKey));

            indexKeySymbol++;
        }
        return word.toString();
    }

    private char getIndexEncodeSymbol(int indexSymbol, int indexKey) {
        if (indexSymbol <= indexKey) {
            int sum = indexSymbol + SIZE_TABLE;
            return table[Math.abs(sum - indexKey)];
        } else {
            return table[(Math.abs(indexSymbol - indexKey))];
        }
    }
}

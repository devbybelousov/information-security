package ru.tusur;

import ru.tusur.matrix.MatrixCryptEncoder;
import ru.tusur.model.CryptEncoder;
import ru.tusur.polib.PolibCryptEncoder;
import ru.tusur.replace.ReplaceCryptEncoder;
import ru.tusur.rsa.RsaCryptEncoder;
import ru.tusur.service.ConsoleInput;
import ru.tusur.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean isRun = true;
        Scanner scanner = new Scanner(System.in);
        ConsoleInput input = new ConsoleInput();

        while (isRun) {
            printMenu();
            String userAnswer = scanner.nextLine();

            isRun = !userAnswer.equalsIgnoreCase("q");

            if (isRun) {
                runCrypt(input, userAnswer);
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите алгоритм шифровки:\n" +
                "1) Метод Полибия.\n" +
                "2) Шифр на основе метода замены.\n" +
                "3) Шифр, основанный на методе умножения матриц.\n" +
                "4) Асимметричный алгоритм шифрования данных RSA.\n" +
                "q) Выйти из программы");
    }

    private static void runCrypt(ConsoleInput input, String userAnswer) {
        Map<String, CryptEncoder> cryptEncoderMap = getCryptEncoderByAnswerUser();

        if (cryptEncoderMap.containsKey(userAnswer)) {
            CryptEncoder cryptEncoder = cryptEncoderMap.get(userAnswer).create();
            String word = input.read("Введите ваше слово:");

            Utils.run(word, cryptEncoder);
        } else {
            System.out.println("Error: Введен неверный ответ!");
        }
    }

    private static Map<String, CryptEncoder> getCryptEncoderByAnswerUser() {
        Map<String, CryptEncoder> cryptEncoderMap = new HashMap<>();
        cryptEncoderMap.put("1", new PolibCryptEncoder());
        cryptEncoderMap.put("2", new ReplaceCryptEncoder());
        cryptEncoderMap.put("3", new MatrixCryptEncoder());
        cryptEncoderMap.put("4", new RsaCryptEncoder());
        return cryptEncoderMap;
    }
}

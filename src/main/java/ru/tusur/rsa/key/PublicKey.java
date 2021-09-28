package ru.tusur.rsa.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tusur.rsa.RsaUtils;

import java.math.BigInteger;
import java.util.Arrays;

@Data
@AllArgsConstructor
public class PublicKey {

    private final BigInteger e;
    private final BigInteger n;

    public byte[] encrypt(byte[] plainText) {
        byte[] cipherText = RsaUtils.modPowByte(plainText, e, n);
        Arrays.fill(plainText, (byte) 0);
        return cipherText;
    }
}

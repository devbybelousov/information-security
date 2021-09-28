package ru.tusur.rsa.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tusur.rsa.RsaUtils;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class PrivateKey {

    private final BigInteger d;
    private final BigInteger n;

    public byte[] decrypt(byte[] cipherText) {
        return RsaUtils.modPowByte(cipherText, d, n);
    }
}

package ru.tusur.rsa;

import ru.tusur.model.CryptEncoder;
import ru.tusur.model.UniversalCryptEncoder;
import ru.tusur.rsa.key.PrivateKey;
import ru.tusur.rsa.key.PublicKey;
import ru.tusur.rsa.key.RsaKeyPair;

public class RsaCryptEncoder extends UniversalCryptEncoder implements CryptEncoder {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public RsaCryptEncoder() {
        RsaKeyPair keyPair = new RsaKeyPair(4096);
        privateKey = keyPair.getPrivateKey();
        publicKey = keyPair.getPublicKey();
    }

    @Override
    public CryptEncoder create() {
        return new RsaCryptEncoder();
    }

    @Override
    public String encode(String var) {
        byte[] plainBytes = var.getBytes();
        byte[] cipherBytes = publicKey.encrypt(plainBytes);

        StringBuilder stringBuilder = new StringBuilder();
        for (byte cipherByte : cipherBytes) {
            stringBuilder.append(cipherByte).append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public String decode(String var) {
        byte[] plainBytes = stringToByte(var);
        byte[] decryptedBytes = privateKey.decrypt(plainBytes);
        return new String(decryptedBytes);
    }

    private byte[] stringToByte(String var) {
        int sizePhrase = var.split(" ").length;
        byte[] plainBytes = new byte[sizePhrase];
        for (int i = 0; i < sizePhrase; i++) {
            plainBytes[i] = Byte.parseByte(var.split(" ")[i]);
        }

        return plainBytes;
    }
}

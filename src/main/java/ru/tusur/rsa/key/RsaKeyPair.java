package ru.tusur.rsa.key;

import lombok.Data;

import java.math.BigInteger;
import java.util.Random;

@Data
public class RsaKeyPair {

    private int bitLength;
    private int pqBitLength;
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger fi;
    private BigInteger e;
    private BigInteger d;
    private PublicKey publicKey;
    private PrivateKey privateKey;


    public RsaKeyPair(int keyBitLength) {
        bitLength = keyBitLength;
        pqBitLength = bitLength / 2;
        do {
            p = BigInteger.probablePrime(pqBitLength, new Random());
            q = BigInteger.probablePrime(pqBitLength, new Random());
            n = p.multiply(q);
        } while(n.bitLength() != bitLength);

        fi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = generatePublicExponent();
        d = e.modInverse(fi);

        publicKey = new PublicKey(e, n);
        privateKey = new PrivateKey(d, n);
    }

    private BigInteger generatePublicExponent() {
        while (true) {
            Random random = new Random();
            int length = pqBitLength + random.nextInt(fi.bitLength() - pqBitLength);
            BigInteger exponent = new BigInteger(length, new Random());

            if (exponent.compareTo(BigInteger.ONE) != 0
                    && exponent.compareTo(fi) < 0
                    && exponent.gcd(fi).compareTo(BigInteger.ONE) == 0) {
                return exponent;
            }
        }
    }
}

package ru.tusur.matrix;

import org.junit.Assert;
import org.junit.Test;

public class MatrixCryptEncoderTest {

    @Test
    public void testEncode() {
        MatrixCryptEncoder matrixCryptEncoder = new MatrixCryptEncoder("12");
        String expected = "Слово";
        String encoded = matrixCryptEncoder.encode(expected);
        System.out.println("Encoded word: " + encoded);

        String decoded = matrixCryptEncoder.decode(encoded);
        System.out.println("Decoded word: " + decoded);

        Assert.assertEquals(decoded, "Слово ");
    }

}


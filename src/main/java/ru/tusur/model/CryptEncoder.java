package ru.tusur.model;

public interface CryptEncoder {

    CryptEncoder create();

    String encode(String var);

    String decode(String var);
}

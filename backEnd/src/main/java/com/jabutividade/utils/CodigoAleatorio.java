package com.jabutividade.utils;

import java.security.SecureRandom;

public class CodigoAleatorio {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMEROS = "0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String gerarCodigo() {
        StringBuilder sb = new StringBuilder(7);

        for (int i = 0; i < 4; i++) {
            sb.append(CARACTERES.charAt(random.nextInt(CARACTERES.length())));
        }

        for (int i = 0; i < 3; i++) {
            sb.append(NUMEROS.charAt(random.nextInt(NUMEROS.length())));
        }

        return sb.toString();
    }
}
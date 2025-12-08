package com.example.demo700.Utils;

import java.io.IOException;
import java.io.InputStream;

public class FileHexConverter {

    public static String fileToHex(InputStream inputStream) throws IOException {
        byte[] bytes = inputStream.readAllBytes();
        StringBuilder hexString = new StringBuilder();

        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    public static byte[] hexToFile(String hex) {
        int length = hex.length();
        byte[] data = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }

        return data;
    }
}

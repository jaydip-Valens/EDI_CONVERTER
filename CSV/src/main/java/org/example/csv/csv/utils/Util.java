package org.example.csv.csv.utils;

import org.example.csv.csv.exceptionHandler.InvalidFileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Util {
    public static void validateFile(MultipartFile ediFile) {
        if (ediFile.isEmpty()) {
            throw new InvalidFileException();
        }
    }

    public static String createStringForSegment(String value, int size) {
        StringBuilder stringBuilder = new StringBuilder(size);
        stringBuilder.append(value);
        if (value.length() < size) {
            stringBuilder.append(" ".repeat(size - value.length()));
        }
        return stringBuilder.toString();
    }
}

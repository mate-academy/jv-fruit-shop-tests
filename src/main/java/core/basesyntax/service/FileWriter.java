package core.basesyntax.service;

import java.io.IOException;

public class FileWriter {
    public void write(String filePath, String text) {
        if (!isValidFormat(filePath)) {
            throw new IllegalArgumentException("Invalid file format. "
                    + "File must be either .txt or .csv");
        }
        try (java.io.FileWriter fileWriter = new java.io.FileWriter(filePath)) {
            fileWriter.write(text);
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + filePath, e);
        }
    }

    private boolean isValidFormat(String filePath) {
        return filePath.endsWith(".txt") || filePath.endsWith(".csv");
    }
}

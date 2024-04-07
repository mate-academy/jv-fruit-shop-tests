package core.basesyntax.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReader {
    public ArrayList<String> read(String fileName) {
        if (!isValidFormat(fileName)) {
            throw new IllegalArgumentException("Invalid file format. "
                    + "File must be either .txt or .csv");
        }
        ArrayList<String> textFromFile = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(fileName))) {
            if (!bufferedReader.readLine().equals("type,fruit,quantity")) {
                throw new IllegalArgumentException("File with invalid structure");
            }
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                textFromFile.add(line);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't find file by path: " + fileName, e);
        }
        return textFromFile;
    }

    private boolean isValidFormat(String filePath) {
        return filePath.endsWith(".txt") || filePath.endsWith(".csv");
    }
}

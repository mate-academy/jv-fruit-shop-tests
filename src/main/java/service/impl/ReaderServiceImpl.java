package service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import service.ReaderService;

public class ReaderServiceImpl implements ReaderService {
    @Override
    public String readFromFile(String fileToRead) {
        if (fileToRead == null) {
            throw new RuntimeException("Unknown path to file:" + fileToRead);
        }
        File fromFile = new File(fileToRead);
        StringBuilder lineFormFile = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fromFile))) {
            String value = reader.readLine();
            if (value.length() == 0) {
                throw new RuntimeException("File is empty:" + fileToRead);
            }
            while (value != null) {
                lineFormFile.append(value).append(System.lineSeparator());
                value = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found:" + fileToRead, e);
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file:" + fileToRead, e);
        }
        return lineFormFile.toString().trim();
    }
}

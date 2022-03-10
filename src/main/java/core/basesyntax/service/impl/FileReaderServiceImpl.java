package core.basesyntax.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public String readFromFile(String filePath) {
        File file = new File(filePath);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String value = reader.readLine();
            while (value != null) {
                builder.append(value).append(System.lineSeparator());
                value = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't open the file: " + filePath, e);
        } catch (IOException e) {
            throw new RuntimeException("Can't read the data from file: " + filePath, e);
        }
        return builder.toString();
    }
}


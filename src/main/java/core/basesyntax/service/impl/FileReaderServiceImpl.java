package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public List<String> read(String fileName) {
        if (fileName == null) {
            throw new RuntimeException("File name can't be null");
        }
        List<String> inputTransactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(fileName))) {
            reader.readLine();
            String value = reader.readLine();
            while (value != null) {
                inputTransactions.add(value);
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file" + fileName, e);
        }
        return inputTransactions;
    }
}

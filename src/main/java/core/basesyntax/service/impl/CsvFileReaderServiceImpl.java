package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReaderServiceImpl implements CsvFileReaderService {

    @Override
    public List<String> readFromFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new RuntimeException("Null or empty fileName");
        }
        List<String> dailyTransactions = new ArrayList<>();
        try {
            dailyTransactions = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + fileName);
        }
        return dailyTransactions;
    }
}

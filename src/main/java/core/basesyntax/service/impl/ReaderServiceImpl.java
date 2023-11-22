package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    private static final int FILE_EXTENSION_INDEX = 1;
    private static final String CSV_EXTENSION = "csv";

    @Override
    public List<String> readFromCsvFile(String filePath) {
        Path path = Paths.get(filePath);
        List<String> transactions;

        if (!filePath.split("\\.")[FILE_EXTENSION_INDEX].equals(CSV_EXTENSION)) {
            throw new RuntimeException("Trying to read non csv file: " + filePath);
        }

        try {
            transactions = Files.readAllLines(path);
            if (transactions.size() > 0) {
                transactions.remove(0);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read a file: " + filePath, e);
        }

        return transactions;
    }
}

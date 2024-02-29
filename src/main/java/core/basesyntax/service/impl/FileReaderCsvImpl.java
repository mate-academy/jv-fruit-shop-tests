package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileReaderCsvImpl implements Reader {
    public static final String CSV_TYPE = ".csv";
    public static final String FIRST_LINE_FORMAT = "type,fruit,quantity";
    public static final int MIN_LINES_NUMBER = 2;
    private static final int COLUMN_NAMES_INDEX = 0;
    private static final int FIRST_LINE_INDEX = 0;

    @Override
    public List<String> read(String fileName) {
        List<String> transactions = new ArrayList<>();
        if (fileName == null) {
            throw new IllegalArgumentException("File path is null");
        }
        if (!fileName.substring(fileName.length() - CSV_TYPE.length()).equals(CSV_TYPE)) {
            throw new RuntimeException("Incorrect file type");
        }
        Path path = Path.of(fileName);
        if (!Files.exists(path)) {
            throw new RuntimeException("File doesn't exist in directory");
        }
        try {
            transactions = Files.readAllLines(path);
            if (transactions.size() < MIN_LINES_NUMBER) {
                throw new RuntimeException("File is empty");
            }
            if (!transactions.get(FIRST_LINE_INDEX).equals(FIRST_LINE_FORMAT)) {
                throw new RuntimeException("""
                        First line format doesn't match with: 'type,fruit,quantity'""");
            }
            transactions.remove(COLUMN_NAMES_INDEX);
        } catch (IOException e) {
            throw new RuntimeException("""
                    Can't read data from file fileName""");
        }
        return transactions;
    }
}

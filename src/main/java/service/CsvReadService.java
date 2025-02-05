package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReadService implements Reader {
    private static final int SKIP_LINES_COUNT = 1;

    @Override
    public List<String> readTransactionsFromCsv(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return br.lines()
                    .skip(SKIP_LINES_COUNT) // Skip header
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + fileName, e);
        }
    }
}

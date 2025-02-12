package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvReadService implements Reader {
    
    @Override
    public List<String> readTransactionsFromCsv(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            return br.lines()
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + fileName,
                    e);
        }
    }
}

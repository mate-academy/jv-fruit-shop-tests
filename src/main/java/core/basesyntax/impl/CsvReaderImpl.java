package core.basesyntax.impl;

import core.basesyntax.service.CsvFileReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvReaderImpl implements CsvFileReader {
    @Override
    public List<String> readFile(String fileName) {
        try (BufferedReader bufferedReader = new
                BufferedReader(new FileReader(fileName))) {
            return bufferedReader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("Can't read file "
                    + fileName, e);
        }
    }
}

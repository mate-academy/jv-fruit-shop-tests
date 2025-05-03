package core.basesyntax.service.impl;

import core.basesyntax.service.DataReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DataReaderFromCsv implements DataReader {
    private static final int ROW_TO_SKIP = 1;
    private static final String EXCEPTION_MESSAGE = "Can't read data from file: ";

    @Override
    public List<String> readData(String file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines()
                    .skip(ROW_TO_SKIP)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_MESSAGE + file);
        }
    }
}

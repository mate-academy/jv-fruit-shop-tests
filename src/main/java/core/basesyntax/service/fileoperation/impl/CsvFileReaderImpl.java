package core.basesyntax.service.fileoperation.impl;

import core.basesyntax.service.fileoperation.CsvFileReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileReaderImpl implements CsvFileReader {
    private static final int MINIMUM_SIZE = 1;

    @Override
    public List<String> inputFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> lineString = reader.lines()
                    .collect(Collectors.toList());
            if (lineString.size() <= MINIMUM_SIZE) {
                throw new RuntimeException("File is empty " + filePath);
            }
            return lineString;
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + filePath, e);
        }
    }
}

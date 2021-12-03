package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileReaderImpl implements CsvFileReader {
    private static final String CSV_SEPARATOR = ",";
    private String filePath;

    public CsvFileReaderImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<String[]> getDataFromFile() {
        List<String> data;
        try {
            data = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file: " + filePath);
        }
        if (data.size() <= 1) {
            throw new RuntimeException("Not enough data in file or file is empty " + filePath);
        }
        return data.stream()
                .skip(1)
                .map(String::trim)
                .map(s -> s.split(CSV_SEPARATOR))
                .collect(Collectors.toList());
    }
}

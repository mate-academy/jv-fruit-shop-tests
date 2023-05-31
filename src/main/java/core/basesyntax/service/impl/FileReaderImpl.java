package core.basesyntax.service.impl;

import core.basesyntax.service.DataReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class FileReaderImpl implements DataReader<List<String>> {
    private final String csvFilePath;

    public FileReaderImpl(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public List<String> readData() {
        try {
            Optional.ofNullable(csvFilePath).orElseThrow(
                    () -> new RuntimeException("File path can`t be null"));
            return Files.readAllLines(Path.of(csvFilePath));
        } catch (IOException e) {
            throw new RuntimeException(String
                    .format("Unable to get data from file by path: %s", csvFilePath));
        }
    }
}

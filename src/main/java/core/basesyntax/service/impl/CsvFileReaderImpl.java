package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvFileReaderImpl implements CsvFileReader {
    private final String filePath;

    public CsvFileReaderImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<String> read() {
        try {
            List<String> strings = Files.readAllLines(Path.of(filePath));
            if (strings.size() == 0) {
                throw new RuntimeException("File '" + filePath + "' is empty");
            }
            return strings;
        } catch (IOException e) {
            throw new RuntimeException("Can't find the file '" + filePath + '\'', e);
        }
    }
}

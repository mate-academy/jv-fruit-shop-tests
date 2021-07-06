package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DataReader implements core.basesyntax.service.Reader {

    public DataReader() {
    }

    @Override
    public List<String> readData(String sourceName) {
        try {
            return Files.readAllLines(Path.of(sourceName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }
}

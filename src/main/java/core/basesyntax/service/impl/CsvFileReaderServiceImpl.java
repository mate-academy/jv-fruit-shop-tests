package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvFileReaderServiceImpl implements ReaderService {
    @Override
    public String read(String fromFile) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> list = Files.readAllLines(Path.of(fromFile));
            list.forEach(str -> builder.append(str).append(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file. " + e);
        }
        return builder.toString();
    }
}

package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {

    @Override
    public List<String> read(String path) {
        List<String> allLines;
        if (path == null) {
            throw new RuntimeException("Path is null");
        }
        try {
            allLines = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file", e);
        }
        if (allLines.isEmpty()) {
            throw new RuntimeException("File is empty");
        }
        return allLines;
    }
}

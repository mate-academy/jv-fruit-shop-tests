package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReaderImpl implements Reader {
    @Override
    public List<String> readFromFile(String pathToFile) {
        if (pathToFile == null || pathToFile.isEmpty()) {
            throw new RuntimeException("Path to file is empty!");
        }
        Path path = Path.of(pathToFile);
        try (Stream<String> stream = Files.lines(path)) {
            List<String> lines = stream.collect(Collectors.toList());
            if (lines.isEmpty()) {
                throw new RuntimeException("File is empty!");
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + pathToFile, e);
        }
    }
}

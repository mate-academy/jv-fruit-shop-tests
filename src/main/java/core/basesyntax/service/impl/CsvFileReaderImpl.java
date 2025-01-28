package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvFileReaderImpl implements CsvFileReader {

    @Override
    public List<String> read(String filename) {
        Path path = Paths.get(filename);
        try (Stream<String> lines = Files.lines(path)) {
            List<String> lineList = lines.collect(Collectors.toList());
            if (lineList.isEmpty()) {
                throw new RuntimeException("Empty CSV file");
            }
            return lineList;
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + filename, e);
        }
    }
}

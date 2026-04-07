package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderImpl implements FileReader {

    @Override
    public List<String> read(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        if (lines.isEmpty()) {
            throw new RuntimeException("File " + filename + " is empty");
        }
        return lines;
    }
}


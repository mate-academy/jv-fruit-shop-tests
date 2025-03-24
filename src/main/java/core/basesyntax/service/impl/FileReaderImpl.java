package core.basesyntax.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import core.basesyntax.service.FileReader;

public class FileReaderImpl implements FileReader {
    @Override
    public List<String> read(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath));
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}

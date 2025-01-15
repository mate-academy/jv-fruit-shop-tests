package core.basesyntax.store.service.impl;

import core.basesyntax.store.service.FileReader;
import core.basesyntax.store.service.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class FileReaderImpl implements FileReader {

    @Override
    public List<String> read(String fileName) {
        Path path = Path.of(fileName);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            Logger.logError("Error reading file: " + path, e);
            try {
                throw new RuntimeException("Failed to read file: " + path, e);
            } catch (RuntimeException ex) {
                Logger.logError("Handled runtime exception: " + ex.getMessage(), ex);
                return Collections.emptyList();
            }
        }
    }
}

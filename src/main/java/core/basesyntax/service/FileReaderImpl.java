package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderImpl implements FileReader {
    @Override
    public List<String> readFromFile(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Something wrong with file - " + fileName, e);
        }
    }
}

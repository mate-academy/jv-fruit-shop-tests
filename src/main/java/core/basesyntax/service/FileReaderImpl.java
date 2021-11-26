package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import service.FileReader;

public class FileReaderImpl implements FileReader {
    @Override
    public List<String> read(String filePath) {
        List<String> input;
        try {
            input = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("file not read");
        }
        return input;
    }
}

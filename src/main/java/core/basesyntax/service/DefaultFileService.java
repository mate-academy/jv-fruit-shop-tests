package core.basesyntax.service;

import core.basesyntax.exception.InvalidFilePathException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DefaultFileService implements FileService {
    @Override
    public List<String> readLines(String filePath) {
        if (filePath == null) {
            throw new InvalidFilePathException("Invalid path: null");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new InvalidFilePathException(
                    String.format("File '%s' does not exist", filePath)
            );
        }

        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Cannot read file '%s'", filePath), e);
        }
    }

    @Override
    public void writeDataToFile(String filePath, String data) {
        if (filePath == null) {
            throw new InvalidFilePathException("Invalid path: null");
        }

        if (data == null) {
            throw new IllegalArgumentException("Invalid data: null");
        }

        try {
            Files.write(Path.of(filePath), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format("Cannot write data to file '%s'", filePath), e
            );
        }
    }
}

package core.basesyntax.service.impl;

import core.basesyntax.exceptions.InvalidFileException;
import core.basesyntax.service.FileReaderService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {

    @Override
    public List<String> readFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.length() == 0) {
                throw new InvalidFileException("File is empty: " + filePath);
            }
            try {
                return Files.readAllLines(Path.of(filePath));
            } catch (IOException e) {
                throw new InvalidFileException("Can`t read content from the file: " + filePath);
            }
        }
        throw new InvalidFileException("File does not exist: " + filePath);
    }
}
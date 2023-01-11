package core.basesyntax.serviceimpl;

import core.basesyntax.service.ReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderServiceImpl implements ReaderService {
    @Override
    public List<String> readFromFile(String pathToFileToRead) {
        File inputFile = new File(pathToFileToRead);
        try {
            return Files.readAllLines(Path.of(pathToFileToRead));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file: " + pathToFileToRead);
        }
    }
}

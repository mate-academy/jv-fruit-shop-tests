package core.basesyntax.service.impl;

import core.basesyntax.exeption.WrongFileFormat;
import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderImpl implements FileReader {
    @Override
    public List<String> readDataFormFile(String filePath) {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new WrongFileFormat("Can't read from file: " + filePath);
        }
    }
}

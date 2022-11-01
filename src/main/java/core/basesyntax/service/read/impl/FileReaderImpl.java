package core.basesyntax.service.read.impl;

import core.basesyntax.service.read.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderImpl implements FileReader {

    @Override
    public List<String> readFromFile(String filePath, String filename) {
        try {
            List<String> fileContent = Files.readAllLines(Path.of(filePath + filename));
            if (fileContent.isEmpty()) {
                throw new RuntimeException("The file is empty!");
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file: " + filePath);
        }
    }
}

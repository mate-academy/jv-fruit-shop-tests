package core.basesyntax.service.impl;

import core.basesyntax.service.ReadFromFile;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadFromFileImpl implements ReadFromFile {
    @Override
    public List<String> read(String fileName) {
        if (fileName == null) {
            throw new RuntimeException("File name cannot be null");
        }
        List<String> informationFromFile;
        try {
            Reader reader = new FileReader(fileName);
            int readSize = reader.read();
            if (readSize == -1) {
                throw new RuntimeException("File is empty" + fileName);
            }
            informationFromFile = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read information from file" + fileName);
        }
        return informationFromFile;
    }
}

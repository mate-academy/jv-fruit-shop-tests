package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    public List<String> readFromFile(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException("File " + fileName + " could not be read", e);
        }
    }
}


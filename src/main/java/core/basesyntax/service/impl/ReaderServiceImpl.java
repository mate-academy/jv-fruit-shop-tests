package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {

    @Override
    public List<String> readFromFile(String filePath) {
        List<String> content;
        try {
            content = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + filePath);
        }
        return content;
    }
}

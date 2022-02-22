package fruitshop.service.impl;

import fruitshop.service.ReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    public List<String> readFromFile(String filePath) {
        if (filePath == null) {
            throw new NullPointerException("Path is null");
        }
        List<String> inputData;
        try {
            inputData = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file");
        }
        return inputData;
    }
}

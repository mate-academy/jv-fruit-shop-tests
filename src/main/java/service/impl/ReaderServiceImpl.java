package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import model.FruitTransaction;
import service.ReaderService;

public class ReaderServiceImpl implements ReaderService {
    private static final String ERROR = "Error reading file: ";
    private final ParseServiceImpl parseService;

    public ReaderServiceImpl(ParseServiceImpl parseService) {
        this.parseService = parseService;
    }

    @Override
    public List<FruitTransaction> readFromFile(String filePath) {
        try {
            List<String> lines = Files.lines(Paths.get(filePath)).toList();
            if (lines.isEmpty()) {
                throw new IllegalArgumentException("File must contain at least a header");
            }
            return Files.lines(Paths.get(filePath))
                .skip(1)
                .map(parseService::parseCsvLine)
                .toList();
        } catch (IOException e) {
            throw new RuntimeException(ERROR + filePath, e);
        }
    }
}

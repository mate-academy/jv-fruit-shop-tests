package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    private static final int FIRST_LINE_INDEX = 0;

    @Override
    public List<String> readFile(String filePath) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(filePath));
        } catch (Exception e) {
            throw new RuntimeException("File " + filePath + " not found.");
        }
        lines.remove(FIRST_LINE_INDEX);
        return lines;
    }
}

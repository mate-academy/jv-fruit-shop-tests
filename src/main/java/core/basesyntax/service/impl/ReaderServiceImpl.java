package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    @Override
    public List<String> readFileToList(File inputFile) {
        if (inputFile == null) {
            throw new RuntimeException("File cannot be null!");
        }
        List<String> inputData;
        try {
            inputData = Files.readAllLines(inputFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't read the file " + inputFile);
        }
        return inputData;
    }
}

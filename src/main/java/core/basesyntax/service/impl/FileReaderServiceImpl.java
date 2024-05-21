package core.basesyntax.service.impl;

import core.basesyntax.exception.ReadingException;
import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public List<String> readFile(String fileName) {
        checkCsvExpansion(fileName);
        List<String> readFromInputFile;
        try {
            readFromInputFile = Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read fileInput: " + fileName);
        }
        return readFromInputFile;
    }

    private void checkCsvExpansion(String fileName) {
        if (!fileName.endsWith(".csv")) {
            throw new ReadingException("The input file isn't csv format.");
        }
    }
}

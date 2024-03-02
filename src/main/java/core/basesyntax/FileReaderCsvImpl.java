package core.basesyntax;

import core.basesyntax.service.impl.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileReaderCsvImpl implements FileReader {
    @Override
    public List<String> readFromFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            List<String> allLines = Files.readAllLines(path);
            if (allLines.size() > 1) {
                return allLines.subList(1, allLines.size());
            } else {
                return Collections.emptyList();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + filePath, e);
        }
    }
}


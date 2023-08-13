package core.basesyntax.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WriterServiceImpl implements WriterService {
    @Override
    public void writeToFile(List<String> data, String outputPath) {
        if (data.isEmpty() || outputPath.isEmpty()) {
            throw new RuntimeException("Incorrect input parameters");
        }
        try {
            Files.write(Path.of(outputPath), data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + outputPath, e);
        }
    }
}

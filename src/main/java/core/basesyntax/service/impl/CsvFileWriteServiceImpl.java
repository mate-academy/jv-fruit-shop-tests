package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class CsvFileWriteServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String report, String filePath) {
        Objects.requireNonNull(report, "Report cannot be null");
        Objects.requireNonNull(filePath, "Filepath cannot be null");
        try (var writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file: " + filePath, e);
        }
    }
}

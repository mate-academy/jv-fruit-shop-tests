package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileWriterService;
import core.basesyntax.service.exception.FileOperationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CsvFileWriterServiceImpl implements CsvFileWriterService {
    @Override
    public void writeToFile(String data, String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.writeString(
                    path,
                    data,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new FileOperationException("Failed to write to file: " + filePath, e);
        }
    }
}

package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CsvFileWriter implements FileWriter {
    @Override
    public void write(String toFileName, String fileContent) {
        try {
            Path filePath = Path.of(toFileName);
            Files.writeString(filePath, fileContent, StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + toFileName, e);
        }
    }
}

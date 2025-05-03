package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvFileWriterServiceImpl implements WriterService {
    @Override
    public void write(String toFileName, String generatedData) {
        try {
            Files.write(Path.of(toFileName), generatedData.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file. " + e);
        }
    }
}

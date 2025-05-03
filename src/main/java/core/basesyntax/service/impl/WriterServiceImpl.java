package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriterServiceImpl implements WriterService {

    @Override
    public void writeInfoToFile(String outputInfo, String outputFilePath) {
        try {
            Files.write(Path.of(outputFilePath), outputInfo.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file " + outputFilePath, e);
        }
    }
}

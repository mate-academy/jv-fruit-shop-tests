package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WriterServiceCsvImpl implements WriterService {
    @Override
    public void writeToFile(String toWrite, String filePath) {
        if (toWrite == null) {
            throw new RuntimeException("Data to write must not be null");
        }
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create a file", e);
        }
        try {
            Files.write(file.toPath(), toWrite.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file", e);
        }
    }
}

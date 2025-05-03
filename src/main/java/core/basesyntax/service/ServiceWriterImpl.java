package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServiceWriterImpl implements ServiceWriter {
    private static final String COMMA = ",";

    @Override
    public boolean writeData(String data, String filePath) {
        try {
            Files.writeString(Path.of(filePath), data);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Can't write to a file " + filePath, e);
        }
    }
}

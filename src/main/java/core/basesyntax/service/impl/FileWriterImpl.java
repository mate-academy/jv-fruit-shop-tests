package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterImpl implements FileWriter {
    @Override
    public boolean writeFile(String filePath, String data) {
        try {
            Files.write(Path.of(filePath), data.getBytes());
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file ", e);
        }
    }
}

package core.basesyntax.service.impl;

import core.basesyntax.exceptions.WriteNotPossibleException;
import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileWriterImpl implements FileWriter {
    @Override
    public void writeToFile(String data, String filePath) {
        if (data == null) {
            throw new RuntimeException("Data to write can't be null");
        }
        if (filePath == null) {
            throw new RuntimeException("File path can't be null");
        }
        File file = new File(filePath);
        try {
            Files.write(file.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new WriteNotPossibleException("Can't write to file " + filePath, e);
        }
    }
}

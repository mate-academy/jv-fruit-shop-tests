package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class CsvFileWriterImpl implements FileWriter {
    @Override
    public void write(String filePath, String text) {
        if (filePath == null) {
            throw new RuntimeException("FilePath is null");
        }

        if (text == null) {
            throw new RuntimeException("Content is null");
        }

        File resultFile = new File(filePath);
        if (!resultFile.exists()) {
            try {
                resultFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Error while creating file " + filePath, e);
            }
        }

        try {
            Files.write(resultFile.toPath(), text.getBytes(), StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Error while writing to file " + filePath, e);
        }
    }
}

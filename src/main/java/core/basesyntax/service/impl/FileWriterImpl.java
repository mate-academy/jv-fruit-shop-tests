package core.basesyntax.service.impl;

import core.basesyntax.FruitShopException;
import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriterImpl implements FileWriter {
    @Override
    public void writeToFile(String fileName, String report) {
        checkParameter(fileName, report);
        try {
            Files.write(Paths.get(fileName), report.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file: " + fileName, e);
        }
    }

    private void checkParameter(String fileName, String report) {
        if (fileName == null) {
            throw new FruitShopException("File path cannot be null");
        }
        if (report == null) {
            throw new FruitShopException("Report cannot be null");
        }
        if (!Files.exists(Path.of(fileName))) {
            throw new FruitShopException("Such file does not exist");
        }
    }
}

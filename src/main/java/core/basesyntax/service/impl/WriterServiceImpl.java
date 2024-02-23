package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class WriterServiceImpl implements WriterService {
    private static final String REPORT_TITLE = "fruit,quantity" + System.lineSeparator();

    @Override
    public void writeToFile(String filePath, String data) {
        if (filePath == null || filePath.isBlank()) {
            throw new RuntimeException("This is an invalid file path = " + filePath);
        }
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create file: " + filePath, e);
        }
        try {
            if (data == null) {
                throw new RuntimeException("No information for writing");
            }
            Files.writeString(file.toPath(), REPORT_TITLE);
            Files.writeString(file.toPath(), data, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to the file " + filePath, e);
        }
    }
}

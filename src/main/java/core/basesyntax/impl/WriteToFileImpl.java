package core.basesyntax.impl;

import core.basesyntax.service.WriteToFileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteToFileImpl implements WriteToFileService {
    @Override
    public void writeToFile(String filePath, String report) {
        try {
            Files.writeString(Path.of(filePath), report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to the " + filePath, e);
        }
    }
}

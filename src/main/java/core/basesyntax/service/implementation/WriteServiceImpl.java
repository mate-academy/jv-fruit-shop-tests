package core.basesyntax.service.implementation;

import core.basesyntax.service.WriteService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WriteServiceImpl implements WriteService {
    @Override
    public void writeToFile(String filePath, String data) {
        if (filePath == null) {
            throw new RuntimeException("File path cannot be null!");
        }
        File file = new File(filePath);
        try {
            Files.write(file.toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file " + filePath, e);
        }
    }
}

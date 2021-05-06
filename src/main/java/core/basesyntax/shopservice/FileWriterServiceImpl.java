package core.basesyntax.shopservice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements WriteService {
    @Override
    public void writeToFile(String data, String filePath) {
        try {
            Files.write(Path.of(filePath), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write to path " + "\"" + filePath + "\"", e);
        }
    }
}

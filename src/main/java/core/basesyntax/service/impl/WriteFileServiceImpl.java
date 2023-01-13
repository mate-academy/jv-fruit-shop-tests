package core.basesyntax.service.impl;

import core.basesyntax.service.WriteFileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteFileServiceImpl implements WriteFileService {
    @Override
    public void writeToFile(String path, String data) {
        Path pathToFile = Path.of(path);
        if (Files.exists(pathToFile)) {
            try {
                Files.writeString(pathToFile, data);
            } catch (IOException e) {
                throw new RuntimeException(
                        String.format("Can`t write data: %s ,to file %s", data, path));
            }
        } else {
            throw new RuntimeException("Can`t write data to this file -> " + path);
        }
    }
}

package core.basesyntax.service.impl;

import core.basesyntax.exception.WrongPathException;
import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterServiceImpl implements FileWriterService {
    @Override
    public void writeToFile(String path, String data) {
        if (path == null) {
            throw new WrongPathException("Can`t write data to file with null path");
        }
        Path pathToFile = Path.of(path);
        if (Files.exists(pathToFile)) {
            try {
                Files.writeString(pathToFile, data);
            } catch (IOException e) {
                throw new RuntimeException(
                        String.format("Can`t write data: %s ,to file %s", data, path));
            }
        } else {
            throw new WrongPathException("Can`t write data to this file -> " + path);
        }
    }
}

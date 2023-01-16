package core.basesyntax.service.impl;

import core.basesyntax.service.FileWriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriteServiceImpl implements FileWriteService {
    @Override
    public void writeToFile(Path path, String data) {
        try {
            Files.writeString(path, data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to the file with name "
                    + path.getFileName(), e);
        }
    }
}

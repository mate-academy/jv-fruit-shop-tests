package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileReaderServiceImpl implements FileReaderService {
    @Override
    public String read(String fileName) throws IOException {
        try {
            return Files.readString(new File(fileName).toPath());
        } catch (IOException e) {
            throw new IOException("Can't read file: " + fileName, e);
        }
    }
}

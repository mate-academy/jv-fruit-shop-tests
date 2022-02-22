package core.basesyntax.service.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileReaderService {
    public static String getFromFile(File file) {
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + file.getPath(), e);
        }
    }
}

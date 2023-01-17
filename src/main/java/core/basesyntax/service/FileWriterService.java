package core.basesyntax.service;

import java.nio.file.Path;

public interface FileWriterService {
    void writeToFile(String data, Path path);
}

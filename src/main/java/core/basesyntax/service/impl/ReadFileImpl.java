package core.basesyntax.service.impl;

import core.basesyntax.service.ReadFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReadFileImpl implements ReadFile {

    @Override
    public List<String> readFile(String pathName) {
        try {
            return Files.readAllLines(Path.of(pathName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from: " + pathName, e);
        }
    }
}

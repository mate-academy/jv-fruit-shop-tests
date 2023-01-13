package core.basesyntax.service.impl;

import core.basesyntax.exeption.InvalidData;
import core.basesyntax.service.ReadFromFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadFromFileImpl implements ReadFromFile {
    @Override
    public String readFile(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new InvalidData("Can't read data from file: " + path);
        }
    }
}

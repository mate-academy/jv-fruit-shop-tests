package core.basesyntax.service.impl;

import core.basesyntax.exception.ReadFileException;
import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderImpl implements FileReader {
    @Override
    public List<String> readFrom(String fileName) {
        try {
            if (fileName == null) {
                throw new ReadFileException();
            }
            return Files.lines(Path.of(fileName)).toList();
        } catch (IOException | NumberFormatException | ReadFileException e) {
            throw new ReadFileException("Can't read file " + fileName);
        }
    }
}

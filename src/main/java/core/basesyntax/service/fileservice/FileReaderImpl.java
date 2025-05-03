package core.basesyntax.service.fileservice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReaderImpl implements FileReader {
    private static final int FIRST_LINE = 0;

    @Override
    public List<String> read(String fileName) {
        List<String> data;
        try {
            data = Files.readAllLines(Path.of(fileName));
            data.remove(FIRST_LINE);
        } catch (IOException | IndexOutOfBoundsException e) {
            throw new RuntimeException("Cant read from file: " + fileName, e);
        }
        return data;
    }
}

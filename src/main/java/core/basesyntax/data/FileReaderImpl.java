package core.basesyntax.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderImpl implements FileReader {

    @Override
    public List<String> read(String path) {

        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path is null or empty");
        }

        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read file " + path, e);
        }
    }
}

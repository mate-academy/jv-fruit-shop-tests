package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileWriterImpl implements FileWriter {

    @Override
    public void write(List<String> report, String path) {
        try {
            Files.write(Path.of(path), report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write report to file " + path, e);
        }
    }
}

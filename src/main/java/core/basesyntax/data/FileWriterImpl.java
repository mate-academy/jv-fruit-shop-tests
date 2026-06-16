package core.basesyntax.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriterImpl implements FileWriter {

    @Override
    public void write(String resultingReport, String path) {

        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Path is null or empty");
        }

        if (resultingReport == null || resultingReport.isEmpty()) {
            throw new IllegalArgumentException("Resulting report is null or empty");
        }

        try {
            Files.writeString(Paths.get(path), resultingReport);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't write file " + path, e);
        }
    }
}

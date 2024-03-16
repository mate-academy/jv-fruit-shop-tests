package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriterImpl implements Writer {
    @Override
    public void writeToFile(String report, String pathToFile) {
        if (pathToFile == null || pathToFile.isEmpty()) {
            throw new RuntimeException("Path to file is empty!");
        }
        if (report == null || report.isEmpty()) {
            throw new RuntimeException("Report is empty!");
        }
        Path path = Path.of(pathToFile);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file. File=" + pathToFile, e);
        }

    }
}

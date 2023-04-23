package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class CsvWriterServiceImpl implements WriterService {
    @Override
    public void writeToFile(String pathToFile, String data) {
        Path path = Path.of(pathToFile);
        File csvOutputFile = path.toFile();
        try (PrintWriter printWriter = new PrintWriter(csvOutputFile)) {
            printWriter.write(data);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't write to file with path:  " + pathToFile, e);
        }
    }
}

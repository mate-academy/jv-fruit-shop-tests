package core.basesyntax.service.implementation;

import core.basesyntax.service.Writer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WriterImpl implements Writer {
    @Override
    public void writeToFile(String filePath, String report) {
        File file = new File(filePath);
        try {
            Files.write(file.toPath(), report.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file", e);
        }
    }
}

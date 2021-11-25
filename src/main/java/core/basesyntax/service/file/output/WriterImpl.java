package core.basesyntax.service.file.output;

import core.basesyntax.exception.MyCustomIOException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriterImpl implements Writer {
    @Override
    public void write(String report, String filePath) {
        try {
            Files.write(Path.of(filePath), report.getBytes());
        } catch (IOException e) {
            throw new MyCustomIOException("Can't write data to file: " + filePath, e);
        }
    }
}

package core.basesyntax.serviceimpl;

import core.basesyntax.service.ReportWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReportWriterServiceImpl implements ReportWriterService {

    @Override
    public void write(String data, String toFilePath) {
        try {
            Files.writeString(Path.of(toFilePath), data);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file" + toFilePath, e);
        }
    }
}

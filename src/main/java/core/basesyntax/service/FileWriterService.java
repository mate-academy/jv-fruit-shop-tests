package core.basesyntax.service;

import java.nio.file.Path;

public interface FileWriterService {
    Path writeReportToFile(String report, String path);
}

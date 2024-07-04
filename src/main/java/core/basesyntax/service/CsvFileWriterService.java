package core.basesyntax.service;

import java.util.List;

public interface CsvFileWriterService {
    void writeToFile(String filePath, List<String> lines);
}

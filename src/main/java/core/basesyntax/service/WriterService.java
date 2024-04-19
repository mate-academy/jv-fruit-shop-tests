package core.basesyntax.service;

import java.util.List;

public interface WriterService {
    void writeToFile(String outputFilePath, List<String> report);
}

package core.basesyntax.service;

import java.util.List;

public interface FileWriterService {
    void writeToFile(List<String> data, String fileName);
}

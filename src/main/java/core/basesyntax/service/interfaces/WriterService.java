package core.basesyntax.service.interfaces;

import java.util.List;

public interface WriterService {
    void writeToFile(String filePath, List<String> lines);
}

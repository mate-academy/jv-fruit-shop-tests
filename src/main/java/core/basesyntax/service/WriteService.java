package core.basesyntax.service;

import java.util.List;

public interface WriteService {
    void writeFile(String fileName, List<String> content);
}

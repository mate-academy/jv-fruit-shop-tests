package core.basesyntax.dao;

import java.util.List;

public interface ReaderService {
    List<String> readFromFile(String filePath);
}

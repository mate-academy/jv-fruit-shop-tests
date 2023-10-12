package core.basesyntax.service.interfaces;

import java.util.List;

public interface ReaderService {
    List<String> readFromFile(String filePath);
}

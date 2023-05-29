package core.basesyntax.services;

import java.util.List;

public interface FileCsvReader {
    List<String> readFromFile(String filePath);
}

package core.basesyntax.service;

import java.util.List;

public interface FileReader {
    List<String> getLinesFromFile(String fileName);
}

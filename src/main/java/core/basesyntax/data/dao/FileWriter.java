package core.basesyntax.data.dao;

import java.util.List;

public interface FileWriter {
    List<String> writeFile(String filePath, String report);
}

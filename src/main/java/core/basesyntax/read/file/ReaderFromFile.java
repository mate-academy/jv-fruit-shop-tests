package core.basesyntax.read.file;

import java.util.List;

public interface ReaderFromFile {
    List<String[]> readFromFile(String filePath);
}

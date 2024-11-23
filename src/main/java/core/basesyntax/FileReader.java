package core.basesyntax;

import java.nio.file.NoSuchFileException;
import java.util.List;

public interface FileReader {
    List<String> read(String filePath) throws NoSuchFileException;
}

package core.basesyntax.basesyntax.service;

import java.io.InputStream;
import java.util.List;

public interface FileReader {
    List<String> readFile(InputStream inputStream);
}

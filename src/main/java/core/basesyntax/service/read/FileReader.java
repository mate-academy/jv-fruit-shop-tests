package core.basesyntax.service.read;

import java.util.List;

public interface FileReader {
    List<String> read(String filePath);

    List<String[]> split(List<String> stringsFromFile);
}

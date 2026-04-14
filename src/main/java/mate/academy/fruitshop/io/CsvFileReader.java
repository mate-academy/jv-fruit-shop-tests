package mate.academy.fruitshop.io;

import java.util.List;

public interface CsvFileReader {
    List<String> read(String filePath);
}

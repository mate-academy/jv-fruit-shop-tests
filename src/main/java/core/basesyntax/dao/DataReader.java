package core.basesyntax.dao;

import java.util.List;

public interface DataReader {
    List<String> readData(String fileName);
}

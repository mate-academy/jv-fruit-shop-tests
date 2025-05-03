package core.basesyntax.strategy.serviceintrface.reader;

import java.util.List;

public interface Reader {
    List<String> readDataFromFile(String path);
}

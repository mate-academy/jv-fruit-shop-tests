package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import java.util.List;

public interface FileWriterService {
    boolean write(String reportFilePath, List<Fruit> fruitsFromStorage);
}

package core.basesyntax.service;

import core.basesyntax.strategy.FruitStrategy;
import java.util.List;

public interface ProcessingService {
    void process(FruitStrategy fruitStrategy, List<String> data);
}

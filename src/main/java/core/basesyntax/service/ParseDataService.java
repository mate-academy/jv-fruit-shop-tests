package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import java.util.List;

public interface ParseDataService {
    List<Fruit> parseData(List<String> dataFromFile);
}

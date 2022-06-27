package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import java.util.List;

public interface ProcessDataService {
    List<Fruit> processData(List<Fruit> parsedData);
}

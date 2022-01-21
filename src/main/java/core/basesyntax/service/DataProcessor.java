package core.basesyntax.service;

import core.basesyntax.model.FruitRecord;
import java.util.List;

public interface DataProcessor {
    void processData(List<FruitRecord> records);
}

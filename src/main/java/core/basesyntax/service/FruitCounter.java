package core.basesyntax.service;

import core.basesyntax.model.FruitRecord;
import java.util.List;

public interface FruitCounter {
    void countFruit(List<FruitRecord> fruitRecordList);
}

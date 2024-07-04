package core.basesyntax.service.processor;

import core.basesyntax.model.FruitRecord;
import java.util.List;
import java.util.Map;

public interface DataProcessorService {
    Map<String, Integer> processData(List<FruitRecord> fruitRecords);
}

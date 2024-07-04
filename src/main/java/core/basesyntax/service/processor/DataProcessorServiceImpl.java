package core.basesyntax.service.processor;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.strategy.TypeStrategy;
import java.util.List;
import java.util.Map;

public class DataProcessorServiceImpl implements DataProcessorService {
    private TypeStrategy typeStrategy;

    public DataProcessorServiceImpl(TypeStrategy typeStrategy) {
        this.typeStrategy = typeStrategy;
    }

    @Override
    public Map<String, Integer> processData(List<FruitRecord> fruitRecords) {
        for (FruitRecord fruitRecord : fruitRecords) {
            typeStrategy.getType(fruitRecord.getOperation()).calculation(fruitRecord);
        }
        return null;
    }
}

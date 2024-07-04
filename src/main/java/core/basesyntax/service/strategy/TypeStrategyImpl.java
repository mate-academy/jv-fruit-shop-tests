package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.strategy.strategyimpl.TypeService;
import java.util.HashMap;

public class TypeStrategyImpl implements TypeStrategy {
    private HashMap<FruitRecord.Operation, TypeService> typeServiceHashMap;

    public TypeStrategyImpl(HashMap<FruitRecord.Operation, TypeService> typeServiceHashMap) {
        this.typeServiceHashMap = typeServiceHashMap;
    }

    @Override
    public TypeService getType(FruitRecord.Operation operation) {
        return typeServiceHashMap.get(operation);
    }
}

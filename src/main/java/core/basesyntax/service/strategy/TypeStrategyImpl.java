package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.strategy.strategyimpl.TypeService;
import java.util.Map;

public class TypeStrategyImpl implements TypeStrategy {
    private Map<FruitRecord.Operation, TypeService> typeServiceMap;

    public TypeStrategyImpl(Map<FruitRecord.Operation, TypeService> typeServiceMap) {
        this.typeServiceMap = typeServiceMap;
    }

    @Override
    public TypeService getType(FruitRecord.Operation operation) {
        return typeServiceMap.get(operation);
    }
}

package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.service.amount.ActivityHandler;
import java.util.Map;

public class TypeActivityStrategyImpl implements TypeActivityStrategy {
    private Map<Operation, ActivityHandler> strategyMap;

    public TypeActivityStrategyImpl(Map<Operation, ActivityHandler> strategyMap) {
        this.strategyMap = strategyMap;
    }

    @Override
    public ActivityHandler get(Operation operation) {
        if (operation == null) {
            throw new NullPointerException("Operation cannot be null");
        }
        return strategyMap.get(operation);
    }
}

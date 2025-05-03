package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.service.strategy.strategyimpl.TypeService;

public interface TypeStrategy {
    TypeService getType(FruitRecord.Operation operation);
}

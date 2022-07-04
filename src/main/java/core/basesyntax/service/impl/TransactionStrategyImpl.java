package core.basesyntax.service.impl;

import core.basesyntax.service.TransactionStrategy;
import core.basesyntax.service.calculation.TransactionCalculation;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    private final Map<String, TransactionCalculation> strategyMap;

    public TransactionStrategyImpl(Map<String, TransactionCalculation> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public TransactionCalculation get(String operation) {

        return strategyMap.get(operation);
    }
}

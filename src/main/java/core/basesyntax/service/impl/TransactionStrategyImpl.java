package core.basesyntax.service.impl;

import core.basesyntax.service.TransactionStrategy;
import core.basesyntax.service.calculation.TransactionCalculation;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    private final Map<String, TransactionCalculation> transactionCalculationMap;

    public TransactionStrategyImpl(Map<String, TransactionCalculation> calculationMap) {
        this.transactionCalculationMap = calculationMap;
    }

    public TransactionCalculation get(String operation) {

        return transactionCalculationMap.get(operation);
    }
}

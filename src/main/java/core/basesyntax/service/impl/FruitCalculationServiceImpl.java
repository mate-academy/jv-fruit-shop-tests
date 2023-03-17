package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitCalculationService;
import core.basesyntax.strategy.CalculatorStrategy;
import java.util.List;

public class FruitCalculationServiceImpl implements FruitCalculationService {
    private final CalculatorStrategy strategy;

    public FruitCalculationServiceImpl(CalculatorStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void addToStorage(List<FruitTransaction> transactions) {
        if (transactions.isEmpty()) {
            throw new RuntimeException("List should be not empty");
        }
        for (FruitTransaction transaction : transactions) {
            strategy.calculate(transaction);
        }
    }
}

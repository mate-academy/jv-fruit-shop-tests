package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.CalculationService;
import core.basesyntax.service.CalculationStrategy;
import java.util.List;

public class CalculationServiceImpl implements CalculationService {
    private CalculationStrategy calculationStrategy;

    public CalculationServiceImpl(CalculationStrategy calculationStrategy) {
        if (calculationStrategy == null) {
            throw new IllegalArgumentException("calculationStrategy is null");
        }
        this.calculationStrategy = calculationStrategy;
    }

    @Override
    public void calculate(List<FruitTransaction> fruitTransactionList) {
        if (fruitTransactionList == null) {
            throw new IllegalArgumentException("fruitTransactionList is null");
        }
        if (fruitTransactionList.isEmpty()) {
            throw new IllegalArgumentException("fruitTransactionList is empty");
        }
        for (FruitTransaction fruit : fruitTransactionList) {
            calculationStrategy.get(fruit.getOperation()).handle(fruit);
        }
    }
}

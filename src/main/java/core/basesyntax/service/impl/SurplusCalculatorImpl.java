package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.SurplusCalculator;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.List;

public class SurplusCalculatorImpl implements SurplusCalculator {

    @Override
    public void calculateData(List<FruitTransaction> fruitTransactions) {
        validateInputData(fruitTransactions);
        fruitTransactions.forEach(fruitTransaction ->
                new OperationStrategy().getOperation(fruitTransaction)
                        .performOperation(fruitTransaction));
    }

    private void validateInputData(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions.isEmpty()) {
            throw new RuntimeException("Input list: \"" + fruitTransactions + "\" can't be empty");
        }
    }
}

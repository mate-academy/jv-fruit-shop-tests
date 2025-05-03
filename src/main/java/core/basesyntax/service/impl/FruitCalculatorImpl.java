package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitCalculator;
import core.basesyntax.strategy.CalculationStrategy;
import java.util.List;

public class FruitCalculatorImpl implements FruitCalculator {
    private final CalculationStrategy calculationStrategy = new CalculationStrategy();

    @Override
    public void calculate(List<FruitTransaction> parsedString) {
        for (FruitTransaction fruitTransaction : parsedString) {
            calculationStrategy
                    .getCalculationServiceByLetter(fruitTransaction)
                    .calculateAndStore(fruitTransaction);
        }
    }
}

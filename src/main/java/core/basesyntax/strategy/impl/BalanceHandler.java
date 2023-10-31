package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import java.util.Objects;

public class BalanceHandler implements OperationHandler {
    @Override
    public Map<String, Integer> handleOperation(FruitTransaction transaction,
                                                Map<String, Integer> fruitQuantities) {
        String fruit = transaction.getFruit().getName();
        int quantity = transaction.getQuantity();

        fruitQuantities.put(fruit, quantity);
        return fruitQuantities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}

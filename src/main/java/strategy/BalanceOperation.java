package strategy;

import java.util.Objects;
import model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public int getQuantityToCalculate(FruitTransaction fruitTransaction) {
        return fruitTransaction.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BalanceOperation;
    }
}

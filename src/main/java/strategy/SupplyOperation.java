package strategy;

import java.util.Objects;
import model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public int getQuantityToCalculate(FruitTransaction fruitTransaction) {
        return fruitTransaction.getQuantity();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SupplyOperation;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}

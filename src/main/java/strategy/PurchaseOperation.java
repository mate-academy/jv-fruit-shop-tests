package strategy;

import java.util.Objects;
import model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public int getQuantityToCalculate(FruitTransaction fruitTransaction) {
        return -fruitTransaction.getQuantity();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PurchaseOperation;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}

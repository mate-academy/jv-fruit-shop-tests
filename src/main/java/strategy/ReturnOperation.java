package strategy;

import java.util.Objects;
import model.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public int getQuantityToCalculate(FruitTransaction fruitTransaction) {
        return fruitTransaction.getQuantity();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ReturnOperation;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}

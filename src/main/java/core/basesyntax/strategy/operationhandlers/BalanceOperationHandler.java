package core.basesyntax.strategy.operationhandlers;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public int getOperation(int quantity) {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }
}

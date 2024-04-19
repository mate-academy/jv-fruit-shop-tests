package core.basesyntax.strategy.operationhandlers;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public int getOperation(int quantity) {
        return Math.negateExact(quantity);
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

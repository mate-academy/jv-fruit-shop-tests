package core.basesyntax.operation;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public int newAmountByOperation(int amount, int newAmount) {
        return amount + newAmount;
    }
}

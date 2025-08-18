package core.basesyntax.strategy;

public class BalanceOperation implements OperationHandler {
    @Override
    public int apply(int currentQuantity, int transactionQuantity) {
        if (transactionQuantity < 0) {
            throw new RuntimeException("Balance quantity cannot be negative: "
                    + transactionQuantity);
        }
        return transactionQuantity;
    }
}

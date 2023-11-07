package core.basesyntax.service.transaction;

public class PurchaseHandler implements OperationHandler {
    private static final String OPERATION_AMOUNT_LESS_THEN_ZERO
            = "Operation amount can not be less than 0";
    private static final String CURRENT_AMOUNT_LESS_THEN_ZERO
            = "Current amount can not be less than 0";
    private static final String BALANCE_LESS_THEN_ZERO
            = "Balance can not be less than 0";

    @Override
    public int countQuantity(int currentAmount, int operationAmount) {
        if (operationAmount < 0) {
            throw new RuntimeException(OPERATION_AMOUNT_LESS_THEN_ZERO);
        }
        if (currentAmount < 0) {
            throw new RuntimeException(CURRENT_AMOUNT_LESS_THEN_ZERO);
        }
        int subtraction = currentAmount - operationAmount;
        if (subtraction < 0) {
            throw new RuntimeException(BALANCE_LESS_THEN_ZERO);
        }
        return subtraction;
    }
}

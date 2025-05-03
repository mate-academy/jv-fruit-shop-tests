package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    private static final String FRUIT_APPLE = "Apple";
    private static final String FRUIT_BANANA = "Banana";
    private static final String FRUIT_ORANGE = "Orange";
    private static final int INITIAL_QUANTITY = 100;
    private static final int UPDATED_QUANTITY = 200;
    private static final int SUPPLY_QUANTITY = 50;
    private static final String EXPECTED_STRING_REPRESENTATION
            = "FruitTransaction{operation=SUPPLY, fruit='Orange', quantity=50}";
    private static final String OPERATION_BALANCE_CODE = "b";
    private static final String OPERATION_SUPPLY_CODE = "s";
    private static final String OPERATION_PURCHASE_CODE = "p";
    private static final String OPERATION_RETURN_CODE = "r";

    @Test
    void constructor_validParameters_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_APPLE, INITIAL_QUANTITY);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals(FRUIT_APPLE, transaction.getFruit());
        assertEquals(INITIAL_QUANTITY, transaction.getQuantity());
    }

    @Test
    void setOperation_validOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, FRUIT_APPLE, INITIAL_QUANTITY);
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        assertEquals(FruitTransaction.Operation.RETURN, transaction.getOperation());
    }

    @Test
    void setFruit_validFruitName_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_APPLE, INITIAL_QUANTITY);
        transaction.setFruit(FRUIT_BANANA);
        assertEquals(FRUIT_BANANA, transaction.getFruit());
    }

    @Test
    void setQuantity_validQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_APPLE, INITIAL_QUANTITY);
        transaction.setQuantity(UPDATED_QUANTITY);
        assertEquals(UPDATED_QUANTITY, transaction.getQuantity());
    }

    @Test
    void toString_validFruitTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, FRUIT_ORANGE, SUPPLY_QUANTITY);
        assertEquals(EXPECTED_STRING_REPRESENTATION, transaction.toString());
    }

    @Test
    void operation_validOperationCode_ok() {
        assertEquals(OPERATION_BALANCE_CODE, FruitTransaction.Operation.BALANCE.getCode());
        assertEquals(OPERATION_SUPPLY_CODE, FruitTransaction.Operation.SUPPLY.getCode());
        assertEquals(OPERATION_PURCHASE_CODE, FruitTransaction.Operation.PURCHASE.getCode());
        assertEquals(OPERATION_RETURN_CODE, FruitTransaction.Operation.RETURN.getCode());
    }
}

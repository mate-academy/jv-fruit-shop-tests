package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 10;
    private static final String INVALID_OPERATION_CODE = "invalid code";
    private static final String VALID_OPERATION_CODE = "b";
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void getOperation_validOperation_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.BALANCE;
        assertEquals(expectedOperation, fruitTransaction.getOperation());
    }

    @Test
    void setOperation_invalidCode_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitTransaction
                .setOperation(FruitTransaction.Operation
                        .getOperationByCode(INVALID_OPERATION_CODE)));
    }

    @Test
    void setOperation_validCode_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation
                .getOperationByCode(VALID_OPERATION_CODE));
        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.BALANCE;
        assertEquals(expectedOperation, fruitTransaction.getOperation());
    }

    @Test
    void getFruit_validFruit_Ok() {
        fruitTransaction.setFruit(FRUIT);
        assertEquals(FRUIT, fruitTransaction.getFruit());
    }

    @Test
    void getQuantity_ValidQuantity_Ok() {
        fruitTransaction.setQuantity(QUANTITY);
        assertEquals(QUANTITY, fruitTransaction.getQuantity());
    }

    @Test
    void setQuantity_negativeValue_NotOk() {
        assertThrows(RuntimeException.class, () ->
                fruitTransaction.setQuantity(-1));
    }
}

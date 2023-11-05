package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String DEFAULT_NAME = "apple";
    private static final int DEFAULT_QUANTITY = 10;
    private static final FruitTransaction.Operation DEFAULT_OPERATION =
            FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation VALID_OPERATION =
            FruitTransaction.Operation.SUPPLY;
    private static final FruitTransaction DEFAULT_TRANSACTION =
            new FruitTransaction(DEFAULT_OPERATION, DEFAULT_NAME, DEFAULT_QUANTITY);

    private static final String DEFAULT_OPERATION_CODE = "b";
    private static final String NOT_VALID_OPERATION_CODE = "m";

    @Test
    void fruitTransactionConstructor_Ok() {
        FruitTransaction validTransaction =
                new FruitTransaction(DEFAULT_OPERATION, DEFAULT_NAME, DEFAULT_QUANTITY);
        assertEquals(DEFAULT_TRANSACTION, validTransaction);
    }

    @Test
    void fruitTransactionNotEquals_Ok() {
        FruitTransaction otherFruitTransaction =
                new FruitTransaction(VALID_OPERATION, DEFAULT_NAME, DEFAULT_QUANTITY);
        assertNotEquals(DEFAULT_TRANSACTION, otherFruitTransaction);
    }

    @Test
    void getValidFruitName_Ok() {
        assertEquals(DEFAULT_TRANSACTION.getFruit(), DEFAULT_NAME);
    }

    @Test
    void getValidQuantity_Ok() {
        assertEquals(DEFAULT_TRANSACTION.getQuantity(), DEFAULT_QUANTITY);
    }

    @Test
    void getValidOperation_Ok() {
        assertEquals(DEFAULT_TRANSACTION.getOperation(), DEFAULT_OPERATION);
    }

    @Test
    void getOperationValidCode_Ok() {
        assertEquals(FruitTransaction.Operation.fromCode(DEFAULT_OPERATION_CODE),
                DEFAULT_OPERATION);
    }

    @Test
    void getOperationCode() {
        assertEquals(DEFAULT_OPERATION_CODE, DEFAULT_OPERATION.getCode());
    }

    @Test
    void getOperationNotValidCode_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.fromCode(NOT_VALID_OPERATION_CODE));
    }
}

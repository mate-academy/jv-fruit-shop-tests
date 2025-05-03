package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String NOT_OPERATION = "Can not find Operation ";
    private static final String INVALID_OPERATION = "a";
    private static final String VALID_OPERATION_BALANCE = "b";
    private static final String VALID_OPERATION_PURCHASE = "p";
    private static final String VALID_OPERATION_SUPPLY = "s";
    private static final String VALID_OPERATION_RETURN = "r";
    private static final String ORANGE = "orange";
    private static final String APPLE = "apple";
    private static final Integer AMOUNT = 10;
    private FruitTransaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, AMOUNT);
    }

    @AfterEach
    void tearDown() {
        Storage.clearStorage();
    }

    @Test
    void getOperation() {
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
    }

    @Test
    void getFruit() {
        assertEquals(APPLE, transaction.getFruit());
    }

    @Test
    void setFruit() {
        transaction.setFruit(ORANGE);
        assertEquals(ORANGE, transaction.getFruit());
    }

    @Test
    void getQuantity() {
        assertEquals((long) AMOUNT, transaction.getQuantity());
    }

    @Test
    public void findRightOperation_validValue_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.findRightOperation(VALID_OPERATION_BALANCE));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.findRightOperation(VALID_OPERATION_PURCHASE));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.findRightOperation(VALID_OPERATION_SUPPLY));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.findRightOperation(VALID_OPERATION_RETURN));
    }

    @Test
    public void findRightOperation_invalidValue_notOk() {
        assertThrows(RuntimeException.class, () ->
                        FruitTransaction.Operation.findRightOperation(INVALID_OPERATION),
                NOT_OPERATION
                        + INVALID_OPERATION);
    }

    @Test
    public void getOperation_validOperation_ok() {
        assertEquals(VALID_OPERATION_BALANCE, FruitTransaction.Operation.BALANCE.getOperation());
        assertEquals(VALID_OPERATION_PURCHASE, FruitTransaction.Operation.PURCHASE.getOperation());
        assertEquals(VALID_OPERATION_SUPPLY, FruitTransaction.Operation.SUPPLY.getOperation());
        assertEquals(VALID_OPERATION_RETURN, FruitTransaction.Operation.RETURN.getOperation());
    }
}

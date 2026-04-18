package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static final String FRUIT_NAME = "banana";
    private static final int QUANTITY = 20;

    @Test
    void constructor_validParams_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_NAME, QUANTITY);

        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals(FRUIT_NAME, transaction.getFruit());
        assertEquals(QUANTITY, transaction.getQuantity());
    }

    @Test
    void fromCode_allValidCodes_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void fromCode_invalidCode_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.fromCode("x"));
    }

    @Test
    void getCode_validOperation_ok() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getCode());
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getCode());
    }
}

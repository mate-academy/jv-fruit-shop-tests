package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

    @Test
    void constructor_validData_fieldsAreSetCorrectly() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 10
        );

        assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        assertEquals("apple", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    void getOperation_balance_returnsCorrectCode() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getCode());
    }

    @Test
    void fromCode_validCode_returnsCorrectOperation() {
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
    }

    @Test
    void fromCode_invalidCode_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x")
        );
    }
}

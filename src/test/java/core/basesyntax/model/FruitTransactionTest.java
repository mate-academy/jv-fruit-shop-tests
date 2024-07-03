package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static final String CORRECT_OPERATION = "s";
    private static final String INCORRECT_OPERATION = "x";

    @Test
    void getOperation_ValidArgument_Ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.SUPPLY;
        assertEquals(operation,
                FruitTransaction.Operation.getOperationByValue(CORRECT_OPERATION));
    }

    @Test
    void getOperation_InvalidArgument_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getOperationByValue(INCORRECT_OPERATION));
    }

    @Test
    void getOperation_EmptyArgument_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getOperationByValue(""));
    }

    @Test
    void create_ValidFields_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 50);
        assertNotNull(transaction);
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(50, transaction.getQuantity());
    }
}

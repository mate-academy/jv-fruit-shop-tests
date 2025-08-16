package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    void operation_getByCode_validCodes_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getByCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getByCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getByCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.getByCode("r"));
    }

    @Test
    void operation_getByCode_invalidCode_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getByCode("x"));
    }

    @Test
    void fruitTransaction_constructor_setsFieldsCorrectly() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE,
                "apple", 100);

        assertEquals("apple", transaction.getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals(100, transaction.getQuantity());
    }
}

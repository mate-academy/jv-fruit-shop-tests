package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private FruitTransaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new FruitTransaction();
    }

    @Test
    void setOperation_validCode_setsOperation() {
        transaction.setOperation("b");
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
    }

    @Test
    void setOperation_invalidCode_throwsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> transaction.setOperation("x"));
        assertNotNull(exception);
    }

    @Test
    void setFruit_validFruit_setsFruit() {
        transaction.setFruit("apple");
        assertEquals("apple", transaction.getFruit());
    }

    @Test
    void setQuantity_validQuantity_setsQuantity() {
        transaction.setQuantity(10);
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    void getOperation_initialState_returnsNull() {
        assertNull(transaction.getOperation());
    }

    @Test
    void getFruit_initialState_returnsNull() {
        assertNull(transaction.getFruit());
    }

    @Test
    void getQuantity_initialState_returnsZero() {
        assertEquals(0, transaction.getQuantity());
    }
}

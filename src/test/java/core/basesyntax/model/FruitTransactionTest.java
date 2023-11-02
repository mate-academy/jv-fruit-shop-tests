package core.basesyntax.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FruitTransactionTest {
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        transaction = new FruitTransaction();
    }

    @Test
    void gettersAndSetters_workProperly_ok() {
        transaction.setOperation(Operation.BALANCE);
        transaction.setFruit("Apple");
        transaction.setQuantity(10);

        assertEquals(Operation.BALANCE, transaction.getOperation());
        assertEquals("Apple", transaction.getFruit());
        assertEquals(10, transaction.getQuantity());
    }

    @Test
    void gettersAndSetters_withNullValues_notOk() {
        transaction.setOperation(null);
        transaction.setFruit(null);
        transaction.setQuantity(0);

        assertNull(transaction.getOperation());
        assertNull(transaction.getFruit());
        assertEquals(0, transaction.getQuantity());
    }
}
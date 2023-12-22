package core.basesyntax.model;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void get_code_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.getByCode("p");
        assertEquals(FruitTransaction.Operation.PURCHASE, operation);
    }

    @Test
    void get_nullCode_notOk() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.Operation.getByCode(null));
    }

    @Test
    void get_invalidCode_notOk() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.Operation.getByCode("invalid"));
    }

    @Test
    void get_code_notOk() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.Operation.getByCode("B"));
    }

    @Test
    void setAndGet_operation_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction.getOperation());
    }

    @Test
    void get_nullFruit_ok() {
        assertNull(fruitTransaction.getFruit());
    }

    @Test
    void get_quantity0_ok() {
        assertEquals(0, fruitTransaction.getQuantity());
    }

    @Test
    void get_quantity_ok() {
        fruitTransaction.setQuantity(15);
        assertEquals(15, fruitTransaction.getQuantity());
    }
}

package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void getByCode_validCode_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.getByCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.getByCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getByCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.getByCode("r"));
    }

    @Test
    void getByCode_invalidCode_notOk() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.Operation.getByCode("a"));
    }

    @Test
    void getByCode_nullCode_notOk() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.Operation.getByCode(null));
    }
}

package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void testOperationGetByCode_Ok() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.Operation.getByCode("Code"));
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.getByCode("b"));
    }

    @Test
    void testConstructor_Ok() {
        FruitTransaction actualFruitTransaction = new FruitTransaction();
        actualFruitTransaction.setFruit("Fruit");
        actualFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        actualFruitTransaction.setQuantity(1);
        assertEquals("Fruit", actualFruitTransaction.getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, actualFruitTransaction.getOperation());
        assertEquals(1, actualFruitTransaction.getQuantity());
    }

    @Test
    void testOperationValueOf_Ok() {
        assertEquals("b", FruitTransaction.Operation.valueOf("BALANCE").getCode());
    }

    @Test
    void testConstructor_NotOk() {
        FruitTransaction actualFruitTransaction = new FruitTransaction();
        actualFruitTransaction.setFruit("Fruit");
        actualFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        actualFruitTransaction.setQuantity(1);
        assertNotEquals("Vegetable", actualFruitTransaction.getFruit());
        assertNotEquals(FruitTransaction.Operation.PURCHASE, actualFruitTransaction.getOperation());
        assertNotEquals(5, actualFruitTransaction.getQuantity());
    }
}


package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static FruitTransaction actualFruitTransaction;

    @BeforeAll
    static void beforeAll() {
        actualFruitTransaction = new FruitTransaction();
    }

    @BeforeEach
    void setUp() {
        actualFruitTransaction.setFruit("Fruit");
        actualFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        actualFruitTransaction.setQuantity(1);
    }

    @Test
    void testFruitTransactionOperationValueOf_Ok() {
        assertEquals("b", FruitTransaction.Operation.valueOf("BALANCE").getCode());
    }

    @Test
    void testFruitTransactionOperationGetByCode_Ok() {
        assertThrows(RuntimeException.class, () -> FruitTransaction.Operation.getByCode("Code"));
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getByCode("b"));
    }

    @Test
    void testFruitTransactionConstructor_Ok() {
        assertEquals("Fruit", actualFruitTransaction.getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, actualFruitTransaction.getOperation());
        assertEquals(1, actualFruitTransaction.getQuantity());
    }

    @Test
    void testFruitTransactionConstructor_NotOk() {
        assertNotEquals("Vegetable", actualFruitTransaction.getFruit());
        assertNotEquals(FruitTransaction.Operation.PURCHASE,
                actualFruitTransaction.getOperation());
        assertNotEquals(5, actualFruitTransaction.getQuantity());
    }
}

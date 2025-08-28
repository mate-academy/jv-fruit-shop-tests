package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.OperationHandler;

class PurchaseOperationTest {
    private static OperationHandler operation;
    private static FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        operation = new PurchaseOperation();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void getOperation_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> operation.getOperation(null, 2));
    }

    @Test
    void getOperation_negativeAmount_notOk() {
        assertThrows(RuntimeException.class, () -> operation.getOperation(fruitTransaction, -50));
    }

    @Test
    void getOperation_negativeNewAmount_notOK() {
        fruitTransaction.setQuantity(50);
        assertThrows(RuntimeException.class, () -> operation.getOperation(fruitTransaction, 2));
    }

    @Test
    void getOperation_negativeDataQuantity_notOk() {
        fruitTransaction.setQuantity(-120);
        assertThrows(RuntimeException.class, () -> operation.getOperation(fruitTransaction, 2));
    }

    @Test
    void getOperation_returnsCorrectQuantity_Ok() {
        fruitTransaction.setQuantity(20);
        assertEquals(0, operation.getOperation(fruitTransaction, 20));
    }
}

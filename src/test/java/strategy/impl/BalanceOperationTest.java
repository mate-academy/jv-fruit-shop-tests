package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.OperationHandler;

class BalanceOperationTest {
    private static OperationHandler balanceOperation;
    private static FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void getOperation_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> balanceOperation.getOperation(null, 0));
    }

    @Test
    void getOperation_correctInput_Ok() {
        fruitTransaction.setQuantity(25);
        assertEquals(25, balanceOperation.getOperation(fruitTransaction, 0));
    }

    @Test
    void getOperation_returnsCorrectQuantity_Ok() {
        fruitTransaction.setQuantity(25);
        assertEquals(fruitTransaction.getQuantity(),
                balanceOperation.getOperation(fruitTransaction, 0));
    }
}

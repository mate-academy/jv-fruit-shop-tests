package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;
    private int storedAmount;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseOperation();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 100);
        storedAmount = 115;
    }

    @Test
    void makeOperation_methodReturnResultEqualsToSubtractionOfParametersAmount_Ok() {
        int expected = storedAmount - fruitTransaction.getQuantity();
        int actual = operationHandler.makeOperation(fruitTransaction, storedAmount);
        assertEquals(expected, actual);
    }

    @Test
    void makeOperation_returnResultIsNegative_NotOk() {
        storedAmount = 50;
        assertThrows(RuntimeException.class, () -> {
            operationHandler.makeOperation(fruitTransaction, storedAmount);
        });
    }
}

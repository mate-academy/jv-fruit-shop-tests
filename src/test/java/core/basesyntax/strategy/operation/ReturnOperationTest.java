package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final int STORED_AMOUNT = 15;
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnOperation();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 100);
    }

    @Test
    void makeOperation_methodReturnResultEqualsToSumOfParametersAmount_Ok() {
        int expected = fruitTransaction.getQuantity() + STORED_AMOUNT;
        int actual = operationHandler.makeOperation(fruitTransaction, STORED_AMOUNT);
        assertEquals(expected, actual);
    }
}

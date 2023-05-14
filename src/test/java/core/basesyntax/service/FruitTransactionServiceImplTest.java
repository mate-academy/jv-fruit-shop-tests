package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.implementation.FruitTransactionServiceImpl;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private static final String INVALID_INPUT_LINE = "banana,0";
    private static final String INPUT_TEST_LINE = "b,banana,20";
    private static final String BANANA_FRUIT = "banana";
    private static final int BANANA_QUANTITY = 20;

    private FruitTransactionService fruitTransactionService = new FruitTransactionServiceImpl();

    @Test
    public void createFruitTransaction_Ok() {
        FruitTransaction expectedFruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, BANANA_FRUIT, BANANA_QUANTITY);
        FruitTransaction actualFruitTransaction = fruitTransactionService
                .createFruitTransaction(INPUT_TEST_LINE);
        assertEquals(expectedFruitTransaction, actualFruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void createFruitTransaction_nullInputLine_notOk() {
        fruitTransactionService.createFruitTransaction(null);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for null input line, but it wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void createFruitTransaction_invalidInputLine_notOk() {
        fruitTransactionService.createFruitTransaction(INVALID_INPUT_LINE);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for invalid input line, but it wasn't");
    }
}

package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnFruitTest {
    private static final FruitTransaction CORRECT_TRANSACTION
            = new FruitTransaction(Operation.RETURN, "banana", 20);
    private static final FruitTransaction CORRECT_FRUIT_THAT_NOT_EXIST
            = new FruitTransaction(Operation.RETURN, "pineapple", 150);
    private static final FruitTransaction INCORRECT_DIFFER_OPERATION
            = new FruitTransaction(Operation.PURCHASE, "banana", 20);
    private static final String NON_EXIST_FRUIT = "pineapple";
    private static ReturnFruit returnFruit;

    @BeforeClass
    public static void initBalanceHandler() {
        returnFruit = new ReturnFruit();
        FruitsStorage.fruitsStorage.clear();
    }

    @Before
    public void fillStorage() {
        FruitsStorage.fruitsStorage.put("banana", 100);
    }

    @Test
    public void makeOperation_ok() {
        Integer expected = 120;
        assertTrue(returnFruit.makeOperation(CORRECT_TRANSACTION));
        assertEquals("Incorrect result after transaction. Expected: "
                + expected + " actual: " + FruitsStorage.fruitsStorage.get("banana"),
                expected, FruitsStorage.fruitsStorage.get("banana"));
    }

    @Test
    public void makeOperation_correctTransactionWithNewFruit_Ok() {
        Integer expected = 150;
        assertTrue(returnFruit.makeOperation(CORRECT_FRUIT_THAT_NOT_EXIST));
        assertEquals("Incorrect result after transaction."
                        + "Expected: " + expected + " actual: "
                        + FruitsStorage.fruitsStorage.get(NON_EXIST_FRUIT),
                expected, FruitsStorage.fruitsStorage.get(NON_EXIST_FRUIT));
    }

    @Test (expected = RuntimeException.class)
    public void makeOperation_incorrectTransaction_notOk() {
        returnFruit.makeOperation(INCORRECT_DIFFER_OPERATION);
    }

    @After
    public void clearStorage() {
        FruitsStorage.fruitsStorage.clear();
    }
}

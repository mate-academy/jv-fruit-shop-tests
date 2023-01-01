package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnHandlerTest {
    private static String FRUIT = "peach";
    private OperationHandler returnHandler;

    @Before
    public void setUp() {
        returnHandler = new ReturnOperationStrategy();
        Storage.fruits.put(FRUIT, 50);
    }

    @Test
    public void returnHandler_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit(FRUIT);
        transaction.setQuantity(25);
        returnHandler.handle(transaction);
        Integer expected = 75;
        Integer actual = Storage.fruits.get(FRUIT);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}

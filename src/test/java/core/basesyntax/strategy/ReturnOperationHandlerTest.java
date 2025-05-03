package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        Storage.storageFruits.put("apple",40);
    }

    @Test
    public void returnOperationHandler_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction("apple", 10);
        returnOperationHandler.handle(fruitTransaction);
        int actual = Storage.storageFruits.get("apple");
        int expected = 50;
        assertEquals(expected, actual);
    }
}

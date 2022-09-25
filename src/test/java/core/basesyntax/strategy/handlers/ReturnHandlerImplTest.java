package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Before;
import org.junit.Test;

public class ReturnHandlerImplTest extends ReturnHandlerImpl {
    private OperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        returnOperationHandler = new ReturnHandlerImpl();
        Storage.storage.put("banana", 10);
    }

    @Test
    public void handle_ValidData_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction("banana", 30);
        returnOperationHandler.handle(fruitTransaction);
        int expected = 40;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }
}

package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SupplyHandlerImplTest extends SupplyHandlerImpl {
    OperationHandler supplyHandlerOperation;

    @Before
    public void setUp() {
        supplyHandlerOperation = new SupplyHandlerImpl();
        Storage.storage.put("banana", 10);
    }
    @Test
    public void SupplyHandler_ValidData_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction("banana", 30);
        supplyHandlerOperation.handle(fruitTransaction);
        int expected = 40;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }
}
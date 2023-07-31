package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.SupplyHandler;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {
    private OperationHandler supplyHandler;

    @Before
    public void setUp() {
        supplyHandler = new SupplyHandler();
    }

    @Test
    public void supply_ValidData_Ok() {
        Storage.storageMap.put("apple", 10);
        supplyHandler.doOperation("apple", 5);
        assertEquals(15, (int) Storage.storageMap.get("apple"));
        Storage.storageMap.clear();
    }
}

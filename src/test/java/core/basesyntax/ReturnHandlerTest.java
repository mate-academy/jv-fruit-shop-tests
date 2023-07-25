package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.ReturnHandler;
import org.junit.Before;
import org.junit.Test;

public class ReturnHandlerTest {
    private OperationHandler returnHandler;

    @Before
    public void setUp() {
        returnHandler = new ReturnHandler();
    }

    @Test
    public void return_ValidData_Ok() {
        Storage.storageMap.put("banana", 8);
        returnHandler.doOperation("banana", 2);
        assertEquals(10, (int) Storage.storageMap.get("banana"));
    }
}

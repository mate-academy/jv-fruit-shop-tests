package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.LineInformation;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandlerReturn;

    @BeforeClass
    public static void setUp() {
        operationHandlerReturn = new ReturnOperationHandler();
    }

    @Test
    public void operate_ok() {
        Fruit banana = new Fruit("banana");
        LineInformation lineInformationReturn =
                new LineInformation("r", banana, 10);
        Storage.storage.put(banana, 40);
        operationHandlerReturn.operate(lineInformationReturn);
        int expected = 50;
        int actual = Storage.storage.get(banana);
        assertEquals(expected, actual);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}

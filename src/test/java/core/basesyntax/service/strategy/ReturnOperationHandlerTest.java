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
    private static Fruit banana;
    private static LineInformation lineInformationReturn;
    private static LineInformation lineInformationBalance;

    @BeforeClass
    public static void setUp() {
        operationHandlerReturn = new ReturnOperationHandler();
        banana = new Fruit("banana");
        lineInformationBalance = new LineInformation("b", banana, 40);
        lineInformationReturn = new LineInformation("r", banana, 10);
    }

    @Test
    public void operate_ok() {
        Storage.storage.put(banana, lineInformationBalance.getQuantity());
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

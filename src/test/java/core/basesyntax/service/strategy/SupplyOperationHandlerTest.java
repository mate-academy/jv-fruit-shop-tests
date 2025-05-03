package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.LineInformation;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static LineInformation lineInformationSupply;
    private static Fruit bananaFruit;

    @BeforeClass
    public static void setUp() {
        operationHandler = new SupplyOperationHandler();
        bananaFruit = new Fruit("banana");
        lineInformationSupply = new LineInformation("s", bananaFruit, 15);
    }

    @Test
    public void operate_ok() {
        Storage.storage.put(bananaFruit, 10);
        operationHandler.operate(lineInformationSupply);
        int expected = 25;
        int actual = Storage.storage.get(bananaFruit);
        assertEquals(expected, actual);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}

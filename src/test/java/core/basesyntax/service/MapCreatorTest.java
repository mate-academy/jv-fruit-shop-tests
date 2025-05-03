package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.MapCreatorImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.AddOperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class MapCreatorTest {
    private static MapCreator mapCreator;

    @BeforeClass
    public static void beforeClass() {
        mapCreator = new MapCreatorImpl();
    }

    @Test
    public void mapCreator_keysCorrectWork_ok() {
        Map<String, OperationHandler> expected = new HashMap<>();
        expected.put("b", new BalanceOperationHandler());
        expected.put("p", new PurchaseOperationHandler());
        expected.put("r", new AddOperationHandler());
        expected.put("s", new AddOperationHandler());
        Map<String, OperationHandler> actual = mapCreator.createMap();
        assertEquals(expected.keySet(), actual.keySet());
    }
}

package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerMapProviderTest {
    private static final FruitStorageDao storageDao = new FruitStorageDaoImpl();
    private static Map<String, OperationHandler> mapProvider;
    private static BalanceHandler balanceHandler;
    private static PurchaseHandler purchaseHandler;
    private static ReturnHandler returnHandler;
    private static SupplyHandler supplyHandler;

    @BeforeClass
    public static void beforeClass() {
        mapProvider = new OperationHandlerMapProvider(storageDao).getMap();
        balanceHandler = new BalanceHandler(storageDao);
        purchaseHandler = new PurchaseHandler(storageDao);
        returnHandler = new ReturnHandler(storageDao);
        supplyHandler = new SupplyHandler(storageDao);
    }

    @Test
    public void operationHandlerMapProvider_ok() {
        Map<String, OperationHandler> actual = mapProvider;
        assertEquals(actual.size(), 4);
        assertTrue(actual.containsKey("b"));
        assertTrue(actual.containsKey("p"));
        assertTrue(actual.containsKey("r"));
        assertTrue(actual.containsKey("s"));
        assertEquals(actual.get("b"), balanceHandler);
        assertEquals(actual.get("p"), purchaseHandler);
        assertEquals(actual.get("r"), returnHandler);
        assertEquals(actual.get("s"), supplyHandler);
    }

    @Test
    public void operationHandlerMapProvider_wrongType_notOk() {
        Map<String, OperationHandler> actual = mapProvider;
        assertFalse(actual.containsKey("q"));
        assertFalse(actual.containsKey(""));
        assertFalse(actual.containsKey(null));
        assertNotEquals(actual.get("r"), purchaseHandler);
    }
}

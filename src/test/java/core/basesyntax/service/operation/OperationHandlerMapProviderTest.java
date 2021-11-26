package core.basesyntax.service.operation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import java.util.Map;
import org.hamcrest.collection.IsMapContaining;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerMapProviderTest {
    private static final FruitStorageDao storageDao = new FruitStorageDaoImpl();
    private static Map<String, OperationHandler> mapProvider;
    private static PurchaseHandler purchaseHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new PurchaseHandler(storageDao);
        mapProvider = new OperationHandlerMapProvider(storageDao).getMap();
    }

    @Test
    public void operationHandlerMapProvider_ok() {
        Map<String, OperationHandler> actual = mapProvider;
        assertThat(actual, not(IsMapContaining.hasEntry("r",
                new PurchaseHandler(storageDao))));
        assertThat(actual, IsMapContaining.hasKey("b"));
        assertThat(actual, IsMapContaining.hasKey("p"));
        assertThat(actual, IsMapContaining.hasKey("r"));
        assertThat(actual, IsMapContaining.hasKey("s"));
        assertThat(actual.size(), is(4));
        assertTrue(actual.containsKey("b"));
    }

    @Test
    public void operationHandlerMapProvider_wrongType_notOk() {
        Map<String, OperationHandler> actual = mapProvider;
        assertFalse(actual.containsKey("q"));
        assertFalse(actual.containsKey(""));
        assertFalse(actual.containsKey(null));
        assertFalse(actual.get("r").equals(purchaseHandler));
        assertThat(actual.size(), is(4));
    }
}

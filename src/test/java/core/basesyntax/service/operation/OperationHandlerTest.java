package core.basesyntax.service.operation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.OperationException;
import java.util.Map;
import org.hamcrest.collection.IsMapContaining;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerTest {
    private static final FruitStorageDao DAO = new FruitStorageDaoImpl();
    private static OperationHandlerMapProvider mapProvider;
    private static BalanceHandler balanceHandler;
    private static PurchaseHandler purchaseHandler;
    private static ReturnHandler returnHandler;
    private static SupplyHandler supplyHandler;
    private static Map<String, Integer> providedMap;

    @BeforeClass
    public static void beforeClass() {
        providedMap = Storage.fruitStorage;
        balanceHandler = new BalanceHandler(DAO);
        purchaseHandler = new PurchaseHandler(DAO);
        returnHandler = new ReturnHandler(DAO);
        supplyHandler = new SupplyHandler(DAO);
        mapProvider = new OperationHandlerMapProvider(DAO);
    }

    @Before
    public void beforeEachTest() {
        providedMap.put("banana", 5);
    }

    @Test
    public void balanceApply_validDAta_ok() {
        balanceHandler.apply("banana", 10);
        Assert.assertNotNull("Provided Map is null;", providedMap);
        Assert.assertEquals("Size mismatch for maps;", 1, providedMap.size());
        Assert.assertTrue("Missing keys in storage;" + providedMap.keySet(),
                providedMap.containsKey("banana"));
        Assert.assertTrue("Missing values in storage;" + providedMap.values(),
                providedMap.containsValue(5));
    }

    @Test
    public void purchaseApply_validData_ok() {
        purchaseHandler.apply("banana", 5);
        Assert.assertNotNull("Provided Map is null;", providedMap);
        Assert.assertEquals("Size mismatch for maps;", 1, providedMap.size());
        Assert.assertTrue("Missing keys in storage;" + providedMap.keySet(),
                providedMap.containsKey("banana"));
        Assert.assertTrue("Missing values in storage;" + providedMap.values(),
                providedMap.containsValue(0));
    }

    @Test(expected = OperationException.class)
    public void purchaseApply_invalidData_notOk() {
        purchaseHandler.apply("banana", 6);
    }

    @Test(expected = OperationException.class)
    public void returnApply_invalidData_notOk() {
        returnHandler.apply("stinkSocks", 6);
    }

    @Test
    public void returnApply_validData_ok() {
        returnHandler.apply("banana", 6);
        Assert.assertNotNull("Provided Map is null;", providedMap);
        Assert.assertEquals("Size mismatch for maps;", 1, providedMap.size());
        Assert.assertTrue("Missing keys in storage;" + providedMap.keySet(),
                providedMap.containsKey("banana"));
        Assert.assertTrue("Missing values in storage;" + providedMap.values(),
                providedMap.containsValue(11));
    }

    @Test
    public void supplyApply_addValidData_ok() {
        supplyHandler.apply("banana", 10);
        Assert.assertNotNull("Provided Map is null;", providedMap);
        Assert.assertEquals("Size mismatch for maps;", 1, providedMap.size());
        Assert.assertTrue("Missing keys in storage;" + providedMap.keySet(),
                providedMap.containsKey("banana"));
        Assert.assertTrue("Missing values in storage;" + providedMap.values(),
                providedMap.containsValue(15));
    }

    @Test
    public void supplyApply_updateValidData_ok() {
        supplyHandler.apply("cherry", 6);
        Assert.assertNotNull("Provided Map is null;", providedMap);
        Assert.assertEquals("Size mismatch for maps;", 2, providedMap.size());
        Assert.assertTrue("Missing keys in storage;" + providedMap.keySet(),
                providedMap.containsKey("cherry"));
        Assert.assertTrue("Missing values in storage;" + providedMap.values(),
                providedMap.containsValue(6));
    }

    @Test
    public void operationHandlerMapProvider_ok() {
        Map<String, OperationHandler> actual = mapProvider.getMap();
        assertThat(actual, not(IsMapContaining.hasEntry("r", purchaseHandler)));
        assertThat(actual, IsMapContaining.hasKey("b"));
        assertThat(actual, IsMapContaining.hasKey("p"));
        assertThat(actual, IsMapContaining.hasKey("r"));
        assertThat(actual, IsMapContaining.hasKey("s"));
        assertThat(actual.size(), is(4));
    }

    @After
    public void afterEachTest() {
        providedMap.clear();
    }
}

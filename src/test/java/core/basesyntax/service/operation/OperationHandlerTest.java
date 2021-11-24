package core.basesyntax.service.operation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.OperationException;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.collection.IsMapContaining;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerTest {
    private static final Map<String, OperationHandler> HANDLER_MAP = new HashMap<>();
    private static OperationHandlerMapProvider mapProvider;
    private static FruitStorageDao dao;
    private static PurchaseHandler purchaseHandler;
    private static ReturnHandler returnHandler;
    private static SupplyHandler supplyHandler;
    private static BalanceHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        HANDLER_MAP.put("p", new PurchaseHandler(dao));
        HANDLER_MAP.put("b", new BalanceHandler(dao));
        HANDLER_MAP.put("r", new ReturnHandler(dao));
        HANDLER_MAP.put("s", new SupplyHandler(dao));
        dao = new FruitStorageDaoImpl();
        supplyHandler = new SupplyHandler(new FruitStorageDaoImpl());
        returnHandler = new ReturnHandler(new FruitStorageDaoImpl());
        purchaseHandler = new PurchaseHandler(new FruitStorageDaoImpl());
        balanceHandler = new BalanceHandler(new FruitStorageDaoImpl());
        mapProvider = new OperationHandlerMapProvider(dao);
    }

    @Before
    public void beforeEachTest() {
        Storage.fruitStorage.put("banana", 5);
    }

    @Test
    public void balanceApply_validDAta_ok() {
        balanceHandler.apply("banana", 10);
    }

    @Test
    public void purchaseApply_validData_ok() {
        purchaseHandler.apply("banana", 5);
    }

    @Test(expected = OperationException.class)
    public void purchaseApply_invalidData_NotOk() {
        purchaseHandler.apply("banana", 6);
    }

    @Test
    public void purchaseApply_validData_Ok() {
        purchaseHandler.apply("banana", 5);
    }

    @Test(expected = OperationException.class)
    public void returnApply_invalidData_NotOk() {
        returnHandler.apply("stinkSocks", 6);
    }

    @Test
    public void returnApply_validData_Ok() {
        returnHandler.apply("banana", 6);
    }

    @Test
    public void supplyApply_addVlidData_Ok() {
        supplyHandler.apply("banana", 6);
    }

    @Test
    public void supplyApply_UpdateValidData_Ok() {
        supplyHandler.apply("cherry", 6);
    }

    @Test
    public void operationHandlerMapProvider_Ok() {
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
        Storage.fruitStorage.clear();
    }
}

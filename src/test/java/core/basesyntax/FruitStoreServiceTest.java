package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.dao.FruitStoreDao;
import core.basesyntax.dao.FruitStoreDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ActionStrategy;
import core.basesyntax.service.ActionStrategyImpl;
import core.basesyntax.service.FruitStoreService;
import core.basesyntax.service.FruitStoreServiceImpl;
import core.basesyntax.service.handler.ActionHandler;
import core.basesyntax.service.handler.BalanceActionHandler;
import core.basesyntax.service.handler.PurchaseActionHandler;
import core.basesyntax.service.handler.ReturnActionHandler;
import core.basesyntax.service.handler.SupplyActionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitStoreServiceTest {
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,12" + System.lineSeparator()
            + "apple,20";
    private static final String TITLE = "type,fruit,quantity";
    private static final String NEGATIVE_QUANTITY_DATA = "p,apple,-1";
    private static final String INVALID_ACTION_DATA = "c,apple,1";
    private static FruitStoreService fruitStoreService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<String, ActionHandler> actionHandlerMap = new HashMap<>();
        actionHandlerMap.put("b", new BalanceActionHandler());
        actionHandlerMap.put("s", new SupplyActionHandler());
        actionHandlerMap.put("p", new PurchaseActionHandler());
        actionHandlerMap.put("r", new ReturnActionHandler());
        ActionStrategy actionStrategy = new ActionStrategyImpl(actionHandlerMap);
        FruitStoreDao fruitStoreDao = new FruitStoreDaoImpl();
        fruitStoreService = new FruitStoreServiceImpl(fruitStoreDao, actionStrategy);
    }

    @Test
    public void createNewReport_equalsReports_returnsTrue() {
        Storage.getFruits().put(new Fruit("banana"), 12);
        Storage.getFruits().put(new Fruit("apple"), 20);
        String actualReport = fruitStoreService.createNewReport();
        assertEquals(EXPECTED_REPORT, actualReport);
    }

    @Test
    public void createNewReport_notEqualsReports_returnsTrue() {
        Storage.getFruits().put(new Fruit("banana"), 12);
        String actualReport = fruitStoreService.createNewReport();
        assertNotEquals(EXPECTED_REPORT, actualReport);
    }

    @Test(expected = RuntimeException.class)
    public void addDataToStorage_negativeQuantity_RuntimeException() {
        List<String> data = new ArrayList<>();
        data.add(TITLE);
        data.add(NEGATIVE_QUANTITY_DATA);
        fruitStoreService.addDataToStorage(data);
    }

    @Test(expected = RuntimeException.class)
    public void addDataToStorage_invalidAction_RuntimeException() {
        List<String> invalidData = new ArrayList<>();
        invalidData.add(TITLE);
        invalidData.add(INVALID_ACTION_DATA);
        fruitStoreService.addDataToStorage(invalidData);
    }

    @Test
    public void addDataToStorage_validData_returnsTrue() {
        Storage.getFruits().put(new Fruit("banana"), 2);
        Storage.getFruits().put(new Fruit("apple"), 10);
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("banana"), 2);
        expectedMap.put(new Fruit("apple"), 10);
        assertEquals(expectedMap, Storage.getFruits());
    }

    @After
    public void afterEachTest() {
        Storage.getFruits().clear();
    }
}

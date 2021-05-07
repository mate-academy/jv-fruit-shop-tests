package core.basesyntax.service.impls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.RecordStrategy;
import core.basesyntax.service.StoreService;
import core.basesyntax.service.handler.BalanceOperationHandler;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseOperationHandler;
import core.basesyntax.service.handler.ReturnOperationHandler;
import core.basesyntax.service.handler.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StoreServiceImplTest {
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,12" + System.lineSeparator()
            + "apple,20";
    private static final String LOG_TITLE = "type,fruit,quantity";
    private static final String NEGATIVE_QUANTITY = "p,apple,-1";
    private static final String UNKNOWN_OPERATION = "c,apple,1";
    private static StoreService storeService;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceOperationHandler());
        operationHandlerMap.put("s", new SupplyOperationHandler());
        operationHandlerMap.put("p", new PurchaseOperationHandler());
        operationHandlerMap.put("r", new ReturnOperationHandler());
        RecordStrategy recordStrategy = new RecordStrategyImpl(operationHandlerMap);
        fruitDao = new FruitDaoImpl();
        storeService = new StoreServiceImpl(fruitDao, recordStrategy);
    }

    @Test
    public void createReport_addTwoFruit_isOk() {
        fruitDao.add(new Fruit("banana"), 12);
        fruitDao.add(new Fruit("apple"), 20);
        String actualReport = storeService.createReport();
        assertEquals(EXPECTED_REPORT, actualReport);
    }

    @Test
    public void createReport_notEqualsReports_isOK() {
        fruitDao.add(new Fruit("banana"), 12);
        String actualReport = storeService.createReport();
        assertNotEquals(EXPECTED_REPORT, actualReport);
    }

    @Test(expected = RuntimeException.class)
    public void processRecords_negativeQuantity_RuntimeException() {
        List<String> data = new ArrayList<>();
        data.add(LOG_TITLE);
        data.add(NEGATIVE_QUANTITY);
        storeService.processRecords(data);
    }

    @Test(expected = RuntimeException.class)
    public void processRecords_unknownOperation_RuntimeException() {
        List<String> data = new ArrayList<>();
        data.add(LOG_TITLE);
        data.add(UNKNOWN_OPERATION);
        storeService.processRecords(data);
    }

    @Test
    public void processRecords_isOk() {
        fruitDao.add(new Fruit("banana"), 2);
        fruitDao.add(new Fruit("apple"), 10);

        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 2);
        expected.put(new Fruit("apple"), 10);

        assertEquals(expected, fruitDao.getAll());
    }

    @After
    public void after() {
        fruitDao.getAll().clear();
    }
}

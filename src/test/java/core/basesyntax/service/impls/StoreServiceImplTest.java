package core.basesyntax.service.impls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StoreServiceImplTest {
    private static final String EXPECTED_REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,12" + System.lineSeparator()
            + "apple,20";
    private static final String LOG_TITLE = "type,fruit,quantity";
    private static final String NEGATIVE_QUANTITY = "p,apple,-1";
    private static final String UNKNOWN_OPERATION = "c,apple,1";
    private static StoreService storeService;

    @BeforeAll
    public static void beforeAll() {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceOperationHandler());
        operationHandlerMap.put("s", new SupplyOperationHandler());
        operationHandlerMap.put("p", new PurchaseOperationHandler());
        operationHandlerMap.put("r", new ReturnOperationHandler());
        RecordStrategy recordStrategy = new RecordStrategyImpl(operationHandlerMap);
        FruitDao fruitDao = new FruitDaoImpl();
        storeService = new StoreServiceImpl(fruitDao, recordStrategy);
    }

    @Test
    public void createReport_addTwoFruit_isOk() {
        Storage.fruits.put(new Fruit("banana"), 12);
        Storage.fruits.put(new Fruit("apple"), 20);
        String actualReport = storeService.createReport();
        assertEquals(EXPECTED_REPORT, actualReport);
    }

    @Test
    public void createReport_notEqualsReports_isOK() {
        Storage.fruits.put(new Fruit("banana"), 12);
        String actualReport = storeService.createReport();
        assertNotEquals(EXPECTED_REPORT, actualReport);
    }

    @Test
    public void processRecords_negativeQuantity_RuntimeException() {
        List<String> data = new ArrayList<>();
        data.add(LOG_TITLE);
        data.add(NEGATIVE_QUANTITY);
        assertThrows(RuntimeException.class, () -> storeService.processRecords(data));
    }

    @Test
    public void processRecords_unknownOperation_RuntimeException() {
        List<String> data = new ArrayList<>();
        data.add(LOG_TITLE);
        data.add(UNKNOWN_OPERATION);
        assertThrows(RuntimeException.class, () -> storeService.processRecords(data));
    }

    @Test
    public void processRecords_isOk() {
        Storage.fruits.put(new Fruit("banana"), 2);
        Storage.fruits.put(new Fruit("apple"), 10);

        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 2);
        expected.put(new Fruit("apple"), 10);

        assertEquals(expected, Storage.fruits);
    }

    @AfterEach
    public void afterEach() {
        Storage.fruits.clear();
    }
}

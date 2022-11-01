package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Store;
import core.basesyntax.model.Operation;
import core.basesyntax.service.DataParserService;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperationImpl;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationImpl;
import core.basesyntax.strategy.operation.ReturnOperationImpl;
import core.basesyntax.strategy.operation.SupplyOperationImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserServiceImplTest {
    private static final String EMPTY_DATA = "";
    private static final String SEPARATOR = System.lineSeparator();
    private static final String DATA = "b,banana,20" + SEPARATOR
            + "b,apple,100" + SEPARATOR
            + "s,banana,100" + SEPARATOR
            + "p,banana,13" + SEPARATOR
            + "r,apple,10" + SEPARATOR
            + "p,apple,20" + SEPARATOR
            + "p,banana,5" + SEPARATOR
            + "s,banana,50" + SEPARATOR;
    private static DataParserService dataParserService;

    @BeforeClass
    public static void beforeClass() {
        Map<String, OperationHandler> operationServiceMap = new HashMap<>();
        operationServiceMap.put(Operation.BALANCE.getOperation(),
                new BalanceOperationImpl());
        operationServiceMap.put(Operation.SUPPLY.getOperation(),
                new SupplyOperationImpl());
        operationServiceMap.put(Operation.PURCHASE.getOperation(),
                new PurchaseOperationImpl());
        operationServiceMap.put(Operation.RETURN.getOperation(),
                new ReturnOperationImpl());
        dataParserService = new DataParserServiceImpl(
                new OperationStrategyImpl(operationServiceMap));
    }

    @Test
    public void parseData_validData_ok() {
        Map<String, Integer> actualStorage = dataParserService.parseData(DATA);
        int expectedSize = 2;
        int actualSize = actualStorage.size();
        assertEquals("Expected size: " + expectedSize,expectedSize, actualSize);
        int expectedBananaQuantity = 152;
        int actualBananaQuantity = actualStorage.get("banana");
        assertEquals("Expected banana quantity: " + expectedBananaQuantity
                        + ", but was: " + actualBananaQuantity,
                expectedBananaQuantity, actualBananaQuantity);
        int expectedAppleQuantity = 90;
        int actualAppleQuantity = actualStorage.get("apple");
        assertEquals("Expected apple quantity" + expectedAppleQuantity
                        + ", but was: " + actualAppleQuantity,
                expectedAppleQuantity, actualAppleQuantity);
    }

    @Test
    public void parseData_emptyString_ok() {
        Map<String, Integer> actualStorage = dataParserService.parseData(EMPTY_DATA);
        int expectedSize = 0;
        int actualSize = actualStorage.size();
        assertEquals("If DATA is empty, storage size should be 0, but was" + actualSize,
                expectedSize, actualSize);
    }

    @Test(expected = RuntimeException.class)
    public void parseData_nullData_notOk() {
        dataParserService.parseData(null);
    }

    @After
    public void tearDown() {
        Store.FRUIT_STORAGE.clear();
    }
}

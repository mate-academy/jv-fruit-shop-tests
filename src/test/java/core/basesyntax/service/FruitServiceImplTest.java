package core.basesyntax.service;

import static core.basesyntax.model.OperationType.BALANCE;
import static core.basesyntax.model.OperationType.PURCHASE;
import static core.basesyntax.model.OperationType.RETURN;
import static core.basesyntax.model.OperationType.SUPPLY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitRecordsDao;
import core.basesyntax.db.FruitRecordsDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.operationstrategy.BalanceOperationHandler;
import core.basesyntax.operationstrategy.OperationHandler;
import core.basesyntax.operationstrategy.OperationStrategy;
import core.basesyntax.operationstrategy.OperationStrategyImpl;
import core.basesyntax.operationstrategy.PurchaseOperationHandler;
import core.basesyntax.operationstrategy.ReturnOperationHandler;
import core.basesyntax.operationstrategy.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static FruitService fruitService;
    private static List<TransactionDto> emptyList;
    private static List<TransactionDto> fullList;
    private static List<TransactionDto> nullList;
    private static Map<Fruit, Integer> expectedMap;
    private static List<String> reportList;
    private List<String> expected;
    private List<String> actual;

    @BeforeClass
    public static void setUp() {
        Map<OperationType, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(RETURN, new ReturnOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        FruitRecordsDao fruitRecordsDao = new FruitRecordsDaoImpl();
        fruitService = new FruitServiceImpl(operationStrategy, fruitRecordsDao);
        emptyList = new ArrayList<>();
        nullList = null;
        fullList = new ArrayList<>();
        fullList.add(new TransactionDto(OperationType.BALANCE, new Fruit("banana"), 20));
        fullList.add(new TransactionDto(OperationType.BALANCE, new Fruit("apple"), 100));
        fullList.add(new TransactionDto(OperationType.SUPPLY, new Fruit("banana"), 100));
        fullList.add(new TransactionDto(OperationType.PURCHASE, new Fruit("banana"), 13));
        expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("banana"), 107);
        expectedMap.put(new Fruit("apple"), 100);
        reportList = new ArrayList<>(List.of("fruit,quantity", "banana,107", "apple,100"));
    }

    @After
    public void clearResults() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void saveFruitRecordsFromFile_emptyList_Ok() {
        fruitService.saveFruitRecordsFromFile(emptyList);
        assertTrue("Test failed! Storage must be empty.", FruitStorage.fruitStorage.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void saveFruitRecordsFromFile_nullList_NotOk() {
        fruitService.saveFruitRecordsFromFile(nullList);
    }

    @Test
    public void saveFruitRecordsFromFile_fullList_Ok() {
        fruitService.saveFruitRecordsFromFile(fullList);
        Map<Fruit, Integer> expected = expectedMap;
        Map<Fruit, Integer> actual = FruitStorage.fruitStorage;
        assertEquals("Test failed! Actual storage not equals expected storage", expected, actual);
    }

    @Test
    public void buildReportToList_fullReport_Ok() {
        expected = reportList;
        FruitStorage.fruitStorage.putAll(expectedMap);
        actual = fruitService.buildReportToList();
        assertEquals("Test failed! Actual reportList not equals expected reportList",
                expected, actual);
    }

    @Test
    public void buildReportToList_reportWithEmptyStorage_Ok() {
        expected = new ArrayList<>(List.of("fruit,quantity"));
        actual = fruitService.buildReportToList();
        assertEquals("Test failed! Actual reportList not equals expected reportList",
                expected, actual);
    }
}

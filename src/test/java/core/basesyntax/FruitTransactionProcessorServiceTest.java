package core.basesyntax;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.FruitTransactionProcessorService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.service.impl.FruitTransactionProcessorServiceImpl;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.PurchaseHandler;
import core.basesyntax.strategy.handlers.ReturnHandler;
import core.basesyntax.strategy.handlers.SupplyHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionProcessorServiceTest {
    private static final Path TEST_FRUIT_CSV_FILE_PATH =
            Path.of("src\\test\\resources\\fruits_test.csv");
    private static final Path TEST_REPORT_FILE_PATH =
            Path.of("src\\test\\resources\\report_test.csv");
    private static List<String> fruitsOperationsList;
    private static Map<String, Integer> expectedFruitsQuantityMap;
    private static FruitTransactionProcessorService fruitTransactionProcessorService;

    @BeforeClass
    public static void initFruitTransactionProcessorService() {
        FruitService fruitService = new FruitServiceImpl(new FruitDaoImpl());
        Map<String, OperationHandler> operationsHandlerMap = new HashMap<>();
        operationsHandlerMap.put("b", new BalanceHandler(fruitService));
        operationsHandlerMap.put("s", new SupplyHandler(fruitService));
        operationsHandlerMap.put("r", new ReturnHandler(fruitService));
        operationsHandlerMap.put("p", new PurchaseHandler(fruitService));
        OperationHandlerStrategy operationHandlerStrategy =
                new OperationHandlerStrategyImpl(operationsHandlerMap);
        fruitTransactionProcessorService =
                new FruitTransactionProcessorServiceImpl(operationHandlerStrategy);
    }

    @BeforeClass
    public static void initFruitsOperationsList() {
        try {
            fruitsOperationsList = Files.readAllLines(TEST_FRUIT_CSV_FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file", e);
        }
    }

    @BeforeClass
    public static void initExpectedFruitsQuantityMap() {
        try {
            List<String> reportLines = Files.readAllLines(TEST_REPORT_FILE_PATH);
            reportLines.remove(0);
            expectedFruitsQuantityMap = reportLines.stream()
                    .map(s -> s.split(","))
                    .collect(
                            Collectors.toMap(x -> x[0], x -> Integer.parseInt(x[1]))
                    );
        } catch (IOException e) {
            throw new RuntimeException("Could not read file", e);
        }
    }

    @After
    public void clearStorage() {
        Storage.fruitsMap.clear();
    }

    @Test
    public void fillStorage_dataCorrectly_Ok() {
        fruitTransactionProcessorService.fillStorage(fruitsOperationsList);
        Assert.assertEquals(expectedFruitsQuantityMap, Storage.fruitsMap);
    }

    @Test (expected = NullPointerException.class)
    public void fillStorage_unknownOperations_NotOk() {
        List<String> unknownFruitsOperationsList = new ArrayList<>();
        unknownFruitsOperationsList.add("type,fruit,quantity");
        unknownFruitsOperationsList.add("b,banana,20");
        unknownFruitsOperationsList.add("b,apple,100");
        unknownFruitsOperationsList.add("s,banana,100");
        unknownFruitsOperationsList.add("x,banana,13");
        unknownFruitsOperationsList.add("x,apple,10");
        unknownFruitsOperationsList.add("x,apple,20");
        unknownFruitsOperationsList.add("x,banana,5");
        unknownFruitsOperationsList.add("s,banana,50");
        fruitTransactionProcessorService.fillStorage(unknownFruitsOperationsList);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void fillStorage_noData_NotOk() {
        fruitTransactionProcessorService.fillStorage(new ArrayList<>());
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void fillStorage_csvFileMismatch_NotOk() {
        List<String> csvMismatchLines = new ArrayList<>();
        csvMismatchLines.add("type,    ,quantity");
        csvMismatchLines.add("!!!!!!!!!!!!");
        csvMismatchLines.add("b    100");
        csvMismatchLines.add("s::banana,100");
        fruitTransactionProcessorService.fillStorage(csvMismatchLines);
    }

    @Test
    public void fillStorage_csvOnlyHeader_Ok() {
        List<String> csvOnlyHeaderLines = new ArrayList<>();
        csvOnlyHeaderLines.add("type,fruit,quantity");
        fruitTransactionProcessorService.fillStorage(csvOnlyHeaderLines);
        Assert.assertTrue(Storage.fruitsMap.isEmpty());
    }
}

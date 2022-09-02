package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import core.basesyntax.strategy.AmountHandler;
import core.basesyntax.strategy.AmountStrategy;
import core.basesyntax.strategy.impl.AmountStrategyImpl;
import core.basesyntax.strategy.impl.BalanceAmountHandler;
import core.basesyntax.strategy.impl.PurchaseAmountHandler;
import core.basesyntax.strategy.impl.ReturnAmountHandler;
import core.basesyntax.strategy.impl.SupplyAmountHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static final String OUTPUT_FILE = "src/test/resources/output.csv";
    private static final String TEST_FILE = "src/test/resources/testInput.csv";
    private static final Fruit EXPECTED_TEST_FRUIT = new Fruit("banana", 152);
    private static FruitDao fruitDao;
    private static WriterService writerService;
    private static ReaderService readerService;
    private static ReportService reportService;
    private static AmountStrategy strategy;
    private static FruitService fruitService;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        readerService = new ReaderServiceImpl();
        writerService = new WriterServiceImpl();
        reportService = new ReportServiceImpl();
        Map<String, AmountHandler> amountHandlerMap = new HashMap<>();
        amountHandlerMap.put("b", new BalanceAmountHandler());
        amountHandlerMap.put("p", new PurchaseAmountHandler());
        amountHandlerMap.put("r", new ReturnAmountHandler());
        amountHandlerMap.put("s", new SupplyAmountHandler());
        strategy = new AmountStrategyImpl(amountHandlerMap);
        fruitService = new FruitServiceImpl(
                strategy, fruitDao, readerService, writerService, reportService);
    }

    @Test
    public void fruitService_processDataTest_ok() {
        fruitService.processData(TEST_FILE, OUTPUT_FILE);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("No such file at " + OUTPUT_FILE, e);
        }
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        Assert.assertEquals(expected, actual);
        int expectedStorageSize = 2;
        int actualStorageSize = Storage.fruits.size();
        Assert.assertEquals(expectedStorageSize, actualStorageSize);
        Fruit actualFruit = Storage.fruits.get(0);
        Assert.assertEquals(EXPECTED_TEST_FRUIT, actualFruit);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}

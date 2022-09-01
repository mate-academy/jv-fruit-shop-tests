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
    private static final String TEST_TEXT = "carrot,25";
    private static final String DEFAULT_REPORT = "fruit,quantity" + System.lineSeparator()
                + "carrot,25" + System.lineSeparator();
    private static final Fruit TEST_FRUIT = new Fruit("carrot", 25);
    private static final String OPERATION_BALANCE = "b";
    private static final String OPERATION_PURCHASE = "p";
    private static final String OPERATION_RETURN = "r";
    private static final String OPERATION_SUPPLY = "s";
    private static FruitDao fruitDao;
    private static WriterService writerService;
    private static ReaderService readerService;
    private static ReportService reportService;
    private static AmountStrategy strategy;
    private AmountHandler amountHandler;

    @BeforeClass
    public static void setUp() throws Exception {
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
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void fruitService_processDataTest_ok() {
        List<String> actual;
        FruitService fruitService = new FruitServiceImpl(
                strategy, fruitDao, readerService, writerService, reportService);
        fruitService.processData(TEST_FILE, OUTPUT_FILE);
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
    }
}

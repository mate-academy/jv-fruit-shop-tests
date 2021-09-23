package core.basesyntax.service.storage;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.operation.AddOperation;
import core.basesyntax.service.operation.Calculator;
import core.basesyntax.service.operation.SubtractionOperation;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StoreServiceImplTest {
    private static StoreService storeService;
    private static OperationStrategy operationStrategy;
    private static FruitsDao fruitsDao;
    private static List<String> currentData;
    private static List<String> incorrectData;
    private static Map<Fruit, Integer> expectedMap;
    private static String INPUT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90";

    @BeforeClass
    public static void setUp() {
        Map<Fruit.OperationType, Calculator> operationMap = new HashMap<>();
        operationMap.put(Fruit.OperationType.BALANCE, new AddOperation());
        operationMap.put(Fruit.OperationType.PURCHASE, new SubtractionOperation());
        operationMap.put(Fruit.OperationType.SUPPLY, new AddOperation());
        operationMap.put(Fruit.OperationType.RETURN, new AddOperation());

        operationStrategy = new OperationStrategyImpl(operationMap);
        fruitsDao = new FruitsDaoImpl();
        storeService = new StoreServiceImpl(operationStrategy, fruitsDao);

        currentData = new ArrayList<>();
        currentData.add("b,banana,20");
        currentData.add("b,apple,100");
        currentData.add("s,banana,100");
        currentData.add("p,banana,13");
        currentData.add("r,apple,10");
        currentData.add("p,apple,20");
        currentData.add("p,banana,5");
        currentData.add("s,banana,50");

        incorrectData = new ArrayList<>();
        incorrectData.add("b,banana,20");
        incorrectData.add("n,apple,100");

        expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("banana"), 152);
        expectedMap.put(new Fruit("apple"), 90);
    }

    @Test
    public void correctData_input_Ok() {
        storeService.applyToDb(currentData);
        Map<Fruit, Integer> actual = Storage.storageFruits;
        Assert.assertEquals(expectedMap, actual);
    }

    @Test
    public void incorrectOperation_inputData_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> storeService.applyToDb(incorrectData));
    }

    @Test
    public void takeDB_report_Ok() {
        Storage.storageFruits.put(new Fruit("banana"), 152);
        Storage.storageFruits.put(new Fruit("apple"), 90);
        String actual = storeService.getDbReport();
        Assert.assertEquals(INPUT, actual);
    }

    @After
    public void clearMap() {
        Storage.storageFruits.clear();
    }
}

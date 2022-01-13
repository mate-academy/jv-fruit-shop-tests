package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitDataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.IncreaseFruitHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.PurchaseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static List<FruitRecordDto> fruitRecordList;
    private static FruitShopService fruitShopService;
    private static String expected;
    private static String actual;

    @BeforeClass
    public static void beforeClass() {
        expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
        Map<String, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put("b", new BalanceHandler());
        handlerMap.put("s", new IncreaseFruitHandler());
        handlerMap.put("p", new PurchaseHandler());
        handlerMap.put("r", new IncreaseFruitHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
        fruitRecordList = new ArrayList<>();
        fruitRecordList.add(new FruitRecordDto("b", new Fruit("banana"), 200));
        fruitRecordList.add(new FruitRecordDto("b", new Fruit("apple"), 20));
        fruitRecordList.add(new FruitRecordDto("s", new Fruit("banana"), 20));
        fruitRecordList.add(new FruitRecordDto("p", new Fruit("apple"), 10));
    }

    @Test
    public void save_correctAmount_ok() {
        int expected = 220;
        fruitShopService.save(fruitRecordList);
        int actual = FruitDataBase.storage.get(new Fruit("banana"));
        assertEquals(expected, actual);
        expected = 10;
        fruitShopService.save(fruitRecordList);
        actual = FruitDataBase.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_ok() {
        FruitDataBase.storage.put(new Fruit("banana"), 152);
        FruitDataBase.storage.put(new Fruit("apple"), 90);
        actual = fruitShopService.createReport();
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        FruitDataBase.storage.clear();
    }
}

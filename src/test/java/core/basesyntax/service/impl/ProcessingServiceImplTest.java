package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ProcessingService;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.FruitStrategyImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProcessingServiceImplTest {
    private static ProcessingService processingService;
    private static FruitStrategy fruitStrategy;
    private static List<String> validStringValues;
    private static Fruit banana;
    private static Fruit apple;
    
    @BeforeClass
    public static void beforeClass() {
        processingService = new ProcessingServiceImpl();
        Map<String, OperationHandler> strategyMap = createHashMapOperations();
        fruitStrategy = new FruitStrategyImpl(strategyMap);
        validStringValues = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,10",
                "s,apple,10",
                "r,banana,5",
                "r,apple,5",
                "p,banana,25",
                "p,apple,25"
        );
        banana = new Fruit("banana");
        apple = new Fruit("apple");
    }

    private static Map<String, OperationHandler> createHashMapOperations() {
        Map<String, OperationHandler> map = new HashMap<>();
        map.put("b", new BalanceOperation());
        map.put("s", new SupplyOperation());
        map.put("p", new PurchaseOperation());
        map.put("r", new ReturnOperation());
        return map;
    }

    @Test
    public void process_normalCase_ok() {
        processingService.process(fruitStrategy, validStringValues);
        boolean checkedStorage = Storage.stock.get(banana) == 10 && Storage.stock.get(apple) == 90;
        Assert.assertTrue(
                "The value in the database must match the results of the method.",
                checkedStorage);
    }

    @Test(expected = NumberFormatException.class)
    public void process_numberFormatExceptionExpected_notOk() {
        processingService.process(fruitStrategy, List.of("b,banana,s"));
    }

    @Test
    public void process_ignoringInvalidOperation_ok() {
        processingService.process(fruitStrategy, List.of("q,banana,100"));
        Assert.assertFalse("The fruit cannot exist in storage.",
                Storage.stock.containsKey(banana));
        processingService.process(fruitStrategy, List.of("b,banana,100", "q,banana,45"));
        Assert.assertEquals("Unknown operation must be ignored.",
                100, (int) Storage.stock.get(banana));
    }

    @After
    public void tearDown() {
        Storage.stock.clear();
    }
}

package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.operation.BalanceHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseHandler;
import core.basesyntax.service.operation.ReturnHandler;
import core.basesyntax.service.operation.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static List<TransactionDto> transactionDtoList;
    private static FruitShopService fruitShopService;
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static Fruit banana;
    private static Fruit apple;
    private static Map<Fruit, Integer> expected;
    private static Map<Fruit, Integer> actual;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandlerMap = new HashMap<>();
        transactionDtoList = new ArrayList<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitShopService = new FruitShopServiceImpl(operationStrategy);
        banana = new Fruit("banana");
        apple = new Fruit("apple");
        expected = new HashMap<>();
        actual = new HashMap<>();
    }

    @Before
    public void setUp() throws Exception {
        operationHandlerMap.put("b", new BalanceHandler());
        operationHandlerMap.put("s", new SupplyHandler());
        operationHandlerMap.put("p", new PurchaseHandler());
        operationHandlerMap.put("r", new ReturnHandler());
    }

    @Test
    public void transact_ok() {
        transactionDtoList.add(new TransactionDto("b",
                banana, 100));
        transactionDtoList.add(new TransactionDto("b",
                apple, 50));
        expected.put(banana, 100);
        expected.put(apple, 50);
        actual = fruitShopService.transact(transactionDtoList, operationStrategy);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void transact_Negative_Amount_notOk() {
        transactionDtoList.add(new TransactionDto("p",
                banana, -10));
        transactionDtoList.add(new TransactionDto("b",
                apple, 50));
        expected.put(banana, 8);
        expected.put(apple, 50);
        actual = fruitShopService.transact(transactionDtoList, operationStrategy);
    }

    @After
    public void tearDown() throws Exception {
        transactionDtoList.clear();
    }
}

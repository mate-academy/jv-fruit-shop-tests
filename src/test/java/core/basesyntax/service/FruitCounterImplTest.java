package core.basesyntax.service;

import static core.basesyntax.db.Storage.fruitQuantity;

import core.basesyntax.model.FruitRecord;
import core.basesyntax.operationtype.BalanceHandler;
import core.basesyntax.operationtype.OperationHandler;
import core.basesyntax.operationtype.PurchaseHandler;
import core.basesyntax.operationtype.ReturnHandler;
import core.basesyntax.operationtype.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitCounterImplTest {
    private OperationStrategy operationStrategy;
    private FruitCounter fruitCounter;

    @Before
    public void setUp() throws Exception {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new BalanceHandler());
        operationHandlerMap.put("p", new PurchaseHandler());
        operationHandlerMap.put("r", new ReturnHandler());
        operationHandlerMap.put("s", new SupplyHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitCounter = new FruitCounterImpl(operationStrategy);

    }

    @Test
    public void countFruit_WithValidData_isOK() {
        Map<String, Integer> expected = new HashMap<>();
        List<FruitRecord> fruitRecords = List.of(
                new FruitRecord("b", "banana", 20),
                new FruitRecord("b", "apple", 100),
                new FruitRecord("r", "apple", 10),
                new FruitRecord("s", "banana", 100),
                new FruitRecord("p", "banana", 13));
        fruitCounter.countFruit(fruitRecords);
        expected.put("banana", 107);
        expected.put("apple", 110);
        Assert.assertEquals(fruitQuantity, expected);
    }

    @Test (expected = RuntimeException.class)
    public void countFruit_PurchaseMoreThanFruitInStorage_ExceptionOk() {
        List<FruitRecord> fruitRecords = List.of(
                new FruitRecord("b", "banana", 20),
                new FruitRecord("b", "apple", 100),
                new FruitRecord("s", "banana", 100),
                new FruitRecord("p", "banana", 1300));
        fruitCounter.countFruit(fruitRecords);
    }
}

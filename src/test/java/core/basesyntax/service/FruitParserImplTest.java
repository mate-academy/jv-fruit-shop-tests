package core.basesyntax.service;

import core.basesyntax.service.operation.BalanceHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseHandler;
import core.basesyntax.service.operation.ReturnHandler;
import core.basesyntax.service.operation.SupplyHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitParserImplTest {
    private static Map<String, OperationHandler> fruitOperationMap = new HashMap<>();
    private static FruitStrategy fruitStrategy;
    private static FruitParser fruitParser;

    @Before
    public void setUp() {
        fruitOperationMap.put("b", new BalanceHandler());
        fruitOperationMap.put("s", new SupplyHandler());
        fruitOperationMap.put("p", new PurchaseHandler());
        fruitOperationMap.put("r", new ReturnHandler());
        fruitStrategy = new FruitStrategyImpl(fruitOperationMap);
        fruitParser = new FruitParserImpl(fruitStrategy);
    }

    @Test(expected = RuntimeException.class)
    public void nullInputData_NotOk() {
        fruitParser.parse(null);
    }

    @Test
    public void parseBalance_Ok() {
        List<String> operations = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,30",
                "b,cherry,40");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 20);
        expected.put("apple", 30);
        expected.put("cherry", 40);
        Map<String, Integer> actual = fruitParser.parse(operations);
        checkEquals(expected, actual);
    }

    @Test
    public void parseSupply_Ok() {
        List<String> operations = List.of("type,fruit,quantity", "b,banana,20", "s,banana,30");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 50);
        Map<String, Integer> actual = fruitParser.parse(operations);
        checkEquals(expected, actual);
    }

    @Test
    public void parsePurchase_Ok() {
        List<String> operations = List.of("type,fruit,quantity", "b,banana,40", "p,banana,10");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 30);
        Map<String, Integer> actual = fruitParser.parse(operations);
        checkEquals(expected, actual);
    }

    @Test
    public void parseReturn_Ok() {
        List<String> operations = List.of("type,fruit,quantity", "b,banana,40", "r,banana,10");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 50);
        Map<String, Integer> actual = fruitParser.parse(operations);
        checkEquals(expected, actual);
    }

    private void checkEquals(Map<String, Integer> expected, Map<String, Integer> actual) {
        for (Map.Entry<String, Integer> entry : expected.entrySet()) {
            Assert.assertTrue(actual.containsKey(entry.getKey()));
            Assert.assertEquals(entry.getValue(), actual.get(entry.getKey()));
        }
    }
}

package core.basesyntax.servise.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyOperationImplTest {
    private static Fruit fruit;
    private static StrategyOperation strategyOperation;

    @BeforeClass
    public static void beforeAll() {
        Map<String, OperationHandler> map = new HashMap<>();
        strategyOperation = new StrategyOperationImpl(map);
        fruit = new Fruit("apple");
        map.put("b", new BalanceOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("r", new ReturnOperationHandler());
        map.put("p", new PurchaseOperationHandler());
    }

    @Test
    public void getOperationBalance_ok() {
        Class<?> expectedClass = BalanceOperationHandler.class;
        Class<?> actualClass = strategyOperation.getOperation(
                new FruitTransaction("b", fruit, 10)).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getOperationPurchase_ok() {
        Class<?> expectedClass = PurchaseOperationHandler.class;
        Class<?> actualClass = strategyOperation.getOperation(
                new FruitTransaction("p", fruit, 15)).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getOperationReturn_ok() {
        Class<?> expectedClass = ReturnOperationHandler.class;
        Class<?> actualClass = strategyOperation.getOperation(
                new FruitTransaction("r", fruit, 5)).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getOperationSupply_ok() {
        Class<?> expectedClass = SupplyOperationHandler.class;
        Class<?> actualClass = strategyOperation.getOperation(
                new FruitTransaction("s", fruit, 1)).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }
}

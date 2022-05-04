package servise.strategy;

import java.util.HashMap;
import java.util.Map;
import model.Fruit;
import model.FruitTransaction;
import operation.BalanceOperationHandler;
import operation.OperationHandler;
import operation.PurchaseOperationHandler;
import operation.ReturnOperationHandler;
import operation.SupplyOperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StrategyOperationImplTest {
    private static final Map<String, OperationHandler> map = new HashMap<>();
    private final Fruit fruit;
    private final StrategyOperation strategyOperation;

    private StrategyOperationImplTest() {
        this.strategyOperation = new StrategyOperationImpl(map);
        this.fruit = new Fruit("apple");
    }

    @BeforeAll
    static void beforeAll() {
        map.put("b", new BalanceOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("r", new ReturnOperationHandler());
        map.put("p", new PurchaseOperationHandler());
    }

    @Test
    void getOperationBalance_ok() {
        Class<?> expectedClass = BalanceOperationHandler.class;
        Class<?> actualClass = strategyOperation.getOperation(
                new FruitTransaction("b", fruit, 10)).getClass();
        Assertions.assertEquals(expectedClass, actualClass);
    }

    @Test
    void getOperationPurchase_ok() {
        Class<?> expectedClass = PurchaseOperationHandler.class;
        Class<?> actualClass = strategyOperation.getOperation(
                new FruitTransaction("p", fruit, 15)).getClass();
        Assertions.assertEquals(expectedClass, actualClass);
    }

    @Test
    void getOperationReturn_ok() {
        Class<?> expectedClass = ReturnOperationHandler.class;
        Class<?> actualClass = strategyOperation.getOperation(
                new FruitTransaction("r", fruit, 5)).getClass();
        Assertions.assertEquals(expectedClass, actualClass);
    }

    @Test
    void getOperationSupply_ok() {
        Class<?> expectedClass = SupplyOperationHandler.class;
        Class<?> actualClass = strategyOperation.getOperation(
                new FruitTransaction("s", fruit, 1)).getClass();
        Assertions.assertEquals(expectedClass, actualClass);
    }
}

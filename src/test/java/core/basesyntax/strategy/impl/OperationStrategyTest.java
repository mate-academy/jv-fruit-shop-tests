package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyTest {
    private Map<String, OperationHandler> mapOperationHandler;
    private FruitTransaction fruitTransaction;
    private String fruitName;
    private OperationStrategy strategy;
    private OperationHandler handler;

    @Before
    public void setUp() {
        mapOperationHandler = new HashMap<>();
        mapOperationHandler.put("b", new BalanceOperationHandler());
        mapOperationHandler.put("p", new PurchaseOperationHandler());
        mapOperationHandler.put("r", new ReturnOperationHandler());
        mapOperationHandler.put("s", new SupplyOperationHandler());
        fruitTransaction = new FruitTransaction("b", new Fruit("banana"), 88);
        fruitName = "banana";
        strategy = new OperationStrategy(mapOperationHandler);
        handler = strategy.getByOperation(fruitTransaction.getOperation());
        handler.apply(fruitTransaction);
    }

    @Test
    public void balanceOperationHandler_88Banana_Ok() {
        Integer expected = 88;
        Integer actual = Storage.storage.get(new Fruit(fruitName));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_100Banana_NotOk() {
        fruitTransaction.setOperation("p");
        fruitTransaction.setCount(100);
        strategy = new OperationStrategy(mapOperationHandler);
        handler = strategy.getByOperation(fruitTransaction.getOperation());
        handler.apply(fruitTransaction);
    }

    @Test
    public void purchaseOperation_80Banana_Ok() {
        fruitTransaction.setOperation("p");
        fruitTransaction.setCount(80);
        strategy = new OperationStrategy(mapOperationHandler);
        handler = strategy.getByOperation(fruitTransaction.getOperation());
        handler.apply(fruitTransaction);
        Integer expected = 8;
        Integer actual = Storage.storage.get(new Fruit(fruitName));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void returnOperation_8Banana_Ok() {
        fruitTransaction.setOperation("r");
        fruitTransaction.setCount(8);
        strategy = new OperationStrategy(mapOperationHandler);
        handler = strategy.getByOperation(fruitTransaction.getOperation());
        handler.apply(fruitTransaction);
        Integer expected = 96;
        Integer actual = Storage.storage.get(new Fruit(fruitName));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supplyOperation_12Banana_Ok() {
        fruitTransaction.setOperation("s");
        fruitTransaction.setCount(12);
        strategy = new OperationStrategy(mapOperationHandler);
        handler = strategy.getByOperation(fruitTransaction.getOperation());
        handler.apply(fruitTransaction);
        Integer expected = 100;
        Integer actual = Storage.storage.get(new Fruit(fruitName));
        Assert.assertEquals(expected, actual);
    }
}

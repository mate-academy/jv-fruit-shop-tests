package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyImplTest {
    private static final StorageDao fruitStorageDao = new StorageDaoImpl();
    private static Strategy strategy;
    private static Map<String, StrategyOperation> operationHandlerMap;

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("RETURN", new ReturnOperationImpl(fruitStorageDao));
        operationHandlerMap.put("PURCHASE", new PurchaseOperationImpl(fruitStorageDao));
        operationHandlerMap.put("BALANCE", new BalanceOperationImpl(fruitStorageDao));
        operationHandlerMap.put("SUPPLY", new SupplyOperationImpl(fruitStorageDao));
        strategy = new StrategyImpl(fruitStorageDao, operationHandlerMap);
    }

    @Test
    public void handle_balance_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(new Fruit("banana"));
        fruitTransaction.setQuantity(10);

        strategy.handle(fruitTransaction);

        int actualQuantity = Storage.storage.get(fruitTransaction.getFruit());
        int expectedQuantity = fruitTransaction.getQuantity();

        Assert.assertEquals(expectedQuantity, actualQuantity);
        Assert.assertTrue(Storage.storage.containsKey(fruitTransaction.getFruit()));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @Test
    public void handle_supply_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit(new Fruit("banana"));
        fruitTransaction.setQuantity(10);

        strategy.handle(fruitTransaction);

        int actualQuantity = Storage.storage.get(fruitTransaction.getFruit());
        int expectedQuantity = fruitTransaction.getQuantity();

        Assert.assertEquals(expectedQuantity, actualQuantity);
        Assert.assertTrue(Storage.storage.containsKey(fruitTransaction.getFruit()));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @Test
    public void handle_return_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit(new Fruit("banana"));
        fruitTransaction.setQuantity(10);

        strategy.handle(fruitTransaction);

        int actualQuantity = Storage.storage.get(fruitTransaction.getFruit());
        int expectedQuantity = fruitTransaction.getQuantity();

        Assert.assertEquals(expectedQuantity, actualQuantity);
        Assert.assertTrue(Storage.storage.containsKey(fruitTransaction.getFruit()));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @Test
    public void handle_purchase_Ok() {
        Storage.storage.put(new Fruit("banana"), 20);

        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(new Fruit("banana"));
        fruitTransaction.setQuantity(10);

        strategy.handle(fruitTransaction);

        int actualQuantity = Storage.storage.get(fruitTransaction.getFruit());
        int expectedQuantity = 10;

        Assert.assertEquals(expectedQuantity, actualQuantity);
        Assert.assertTrue(Storage.storage.containsKey(fruitTransaction.getFruit()));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}

package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceImplTest {
    private static ShopService shopService;

    @BeforeClass
    public static void init() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        shopService = new ShopServiceImpl(operationStrategy);
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction("apple",
                FruitTransaction.Operation.BALANCE, 10));
        fruitTransactions.add(new FruitTransaction("lemon",
                FruitTransaction.Operation.BALANCE, 23));
        fruitTransactions.add(new FruitTransaction("lemon",
                FruitTransaction.Operation.PURCHASE, 5));
        fruitTransactions.add(new FruitTransaction("apple", FruitTransaction.Operation.SUPPLY, 34));
        fruitTransactions.add(new FruitTransaction("lemon", FruitTransaction.Operation.RETURN, 2));
        shopService.process(fruitTransactions);
    }

    @Test
    public void process_correctlyRecordedAmountOfFruits_Ok() {
        int expectedSize = 2;
        int actualSize = Storage.fruits.size();
        Assert.assertEquals("Test failed! Expected amount of fruits in storage: "
                + expectedSize + " but was: " + actualSize, expectedSize, actualSize);
    }

    @Test
    public void process_correctlyCalculatedQuantity_Ok() {
        int expectedAppleQuantity = 44;
        int expectedLemonQuantity = 20;
        List<Fruit> fruits = Storage.fruits;
        Fruit apple = fruits.stream()
                .filter(fruit -> fruit.getFruitName().equals("apple"))
                .findFirst()
                .get();
        int actualAppleQuantity = apple.getQuantity();
        Fruit lemon = fruits.stream()
                .filter(fruit -> fruit.getFruitName().equals("lemon"))
                .findFirst()
                .get();
        int actualLemonQuantity = lemon.getQuantity();
        Assert.assertEquals("Test failed! Expected quantity of apple: "
                + expectedAppleQuantity + "but was: " + actualAppleQuantity,
                expectedAppleQuantity, actualAppleQuantity);
        Assert.assertEquals("Test failed! Expected quantity of lemon: "
                + expectedLemonQuantity + "but was: " + actualLemonQuantity,
                expectedLemonQuantity, actualLemonQuantity);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}

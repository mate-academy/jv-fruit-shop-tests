package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceImplTest {
    private static List<FruitTransaction> fruitTransactions;
    private static FruitService fruitService;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitService = new FruitServiceImpl(operationStrategy);
        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction("apple",30,
                FruitTransaction.Operation.BALANCE));
        fruitTransactions.add(new FruitTransaction("apple",20,
                FruitTransaction.Operation.PURCHASE));
        fruitTransactions.add(new FruitTransaction("apple",5,
                FruitTransaction.Operation.RETURN));
        fruitTransactions.add(new FruitTransaction("apple",10,
                FruitTransaction.Operation.SUPPLY));
        fruitTransactions.add(new FruitTransaction("banana",20,
                FruitTransaction.Operation.BALANCE));
        fruitTransactions.add(new FruitTransaction("banana",5,
                FruitTransaction.Operation.PURCHASE));
        fruitTransactions.add(new FruitTransaction("banana",15,
                FruitTransaction.Operation.SUPPLY));
    }

    @Test
    public void fruitService_validData_ok() {
        int expected = 25;
        fruitService.process(fruitTransactions);
        int actual = Storage.storage.get("apple");
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.storage.clear();
    }
}

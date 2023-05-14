package core.basesyntax.service;

import core.basesyntax.database.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.TransactionServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandleImpl;
import core.basesyntax.strategy.impl.OperationHandleStrategyImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandleImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static List<FruitTransaction> transactionData;
    private static Map<Operation, OperationHandler> dateOperation;
    private static OperationHandlerStrategy testStrategy;
    private static TransactionService transactionService;
    private static Fruit banana;

    @BeforeClass
    public static void setUp() {
        banana = new Fruit("banana");
        transactionData = new ArrayList<>();
        transactionData.add(new FruitTransaction(Operation.BALANCE, banana, 100));
        transactionData.add(new FruitTransaction(Operation.SUPPLY, banana, 100));
        dateOperation = new HashMap<>();
        dateOperation.put(Operation.BALANCE, new BalanceOperationHandleImpl());
        dateOperation.put(Operation.SUPPLY, new SupplyOperationHandleImpl());
        testStrategy = new OperationHandleStrategyImpl(dateOperation);
        transactionService = new TransactionServiceImpl(testStrategy);
    }

    @After
    public void clearStorage() {
        FruitStorage.fruitStorage.remove(banana);
    }

    @Test
    public void setTestTransaction_ok() {
        int expected = 200;
        transactionService.transferToStorage(transactionData);
        int actual = FruitStorage.fruitStorage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setEmptyTransaction_ok() {
        transactionService.transferToStorage(Collections.emptyList());
    }
}

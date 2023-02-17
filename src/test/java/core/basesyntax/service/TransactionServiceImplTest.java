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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static List<FruitTransaction> testTransactionData;
    private static Map<Operation, OperationHandler> testDateOperation;
    private static OperationHandlerStrategy testStrategy;
    private static TransactionService testTransaction;
    private static Fruit banana;

    @BeforeClass
    public static void setUp() {
        banana = new Fruit("banana");
        testTransactionData = new ArrayList<>();
        testTransactionData.add(new FruitTransaction(Operation.BALANCE, banana, 100));
        testTransactionData.add(new FruitTransaction(Operation.SUPPLY, banana, 100));
        testDateOperation = new HashMap<>();
        testDateOperation.put(Operation.BALANCE, new BalanceOperationHandleImpl());
        testDateOperation.put(Operation.SUPPLY, new SupplyOperationHandleImpl());
        testStrategy = new OperationHandleStrategyImpl(testDateOperation);
        testTransaction = new TransactionServiceImpl(testStrategy);
    }

    @Before
    public void beforeAll() {
        FruitStorage.fruitStorage.remove(banana);
    }

    @Test
    public void setTestTransaction_ok() {
        int expected = 200;
        testTransaction.transferToStorage(testTransactionData);
        int actual = FruitStorage.fruitStorage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setEmptyTransaction_ok() {
        testTransaction.transferToStorage(Collections.emptyList());
    }
}

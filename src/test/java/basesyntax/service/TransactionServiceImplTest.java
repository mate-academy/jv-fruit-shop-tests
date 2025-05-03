package basesyntax.service;

import static core.basesyntax.model.Operation.BALANCE;
import static core.basesyntax.model.Operation.SUPPLY;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.impl.TransactionServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.OperationHandlerBalanceImpl;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.OperationHandlerSupplyImpl;
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
    private static Map<String, OperationHandler> testDateOperation;
    private static OperationHandlerStrategy testStrategy;
    private static TransactionService testTransaction;
    private static Fruit banana;

    @BeforeClass
    public static void setUp() {
        banana = new Fruit("banana");
        testTransactionData = new ArrayList<>();
        testTransactionData.add(new FruitTransaction("b", banana, 100));
        testTransactionData.add(new FruitTransaction("s", banana, 100));
        testDateOperation = new HashMap<>();
        testDateOperation.put(BALANCE.chooseOperation(), new OperationHandlerBalanceImpl());
        testDateOperation.put(SUPPLY.chooseOperation(), new OperationHandlerSupplyImpl());
        testStrategy = new OperationHandlerStrategyImpl(testDateOperation);
        testTransaction = new TransactionServiceImpl(testStrategy);
    }

    @Before
    public void beforeAll() {
        FruitStorage.fruitStorage.remove(banana);
    }

    @Test
    public void setTestTransaction_ok() {
        int expected = 200;
        testTransaction.addTransferToStorage(testTransactionData);
        int actual = FruitStorage.fruitStorage.get(banana);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setEmptyTransaction_ok() {
        testTransaction.addTransferToStorage(Collections.emptyList());
    }

    @Test
    public void setEmptyTransaction_emptyDb_ok() {
        testTransaction.addTransferToStorage(Collections.emptyList());
        assertTrue(FruitStorage.fruitStorage.isEmpty());
    }
}

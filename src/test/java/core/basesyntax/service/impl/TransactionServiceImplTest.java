package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.OperationHandlerImplBalance;
import core.basesyntax.strategy.impl.OperationHandlerImplPurchase;
import core.basesyntax.strategy.impl.OperationHandlerImplReturn;
import core.basesyntax.strategy.impl.OperationHandlerImplStrategy;
import core.basesyntax.strategy.impl.OperationHandlerImplSupply;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionServiceImplTest {
    private Map<String, OperationHandler> operationHandlerMap;
    private List<Transaction> testTransaction;
    private OperationHandlerStrategy operationHandlerStrategy;
    private TransactionService transactionService;

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("b", new OperationHandlerImplBalance());
        operationHandlerMap.put("p", new OperationHandlerImplPurchase());
        operationHandlerMap.put("r", new OperationHandlerImplReturn());
        operationHandlerMap.put("s", new OperationHandlerImplSupply());
        testTransaction = new ArrayList<>();
        operationHandlerStrategy = new OperationHandlerImplStrategy(operationHandlerMap);
        testTransaction.add(new Transaction("b", new Fruit("banana"), 20));
        testTransaction.add(new Transaction("b", new Fruit("apple"), 100));
        testTransaction.add(new Transaction("s", new Fruit("banana"), 100));
        transactionService = new TransactionServiceImpl(operationHandlerStrategy);
        transactionService.addTransferToStorage(testTransaction);
    }

    @Test
    public void transactionService_Ok() {
        int expected = 100;
        int actual = Storage.fruits.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void storageSize_Ok() {
        Storage.fruits.clear();
        Storage.fruits.put(new Fruit("banana"), 20);
        Assert.assertEquals(1, Storage.fruits.size());
    }
}

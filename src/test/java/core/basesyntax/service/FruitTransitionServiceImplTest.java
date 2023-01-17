package core.basesyntax.service;

import static core.basesyntax.model.FruitTransaction.FruitOperation.BALANCE;
import static core.basesyntax.model.FruitTransaction.FruitOperation.SUPPLY;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.SupplyHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransitionServiceImplTest {
    private static FruitTransactionService transactionService;
    private static Fruit banana;

    @BeforeClass
    public static void setUp() {
        List<FruitTransaction> transactions = new ArrayList<>();
        banana = new Fruit("banana");
        transactions.add(new FruitTransaction("b", banana, 100));
        transactions.add(new FruitTransaction("s", banana, 100));
        Map<String, OperationHandler> dataOperation = new HashMap<>();
        dataOperation.put(BALANCE.getFirstLetter(), new BalanceHandler());
        dataOperation.put(SUPPLY.getFirstLetter(), new SupplyHandler());
        OperationHandlerStrategy strategy = new OperationHandlerStrategyImpl(dataOperation);
        transactionService = new FruitTransactionServiceImpl(strategy);
    }

    @After
    public void clearStorage() {
        FruitStorage.storage.clear();
    }

    @Test
    public void processTransactionService_ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        banana = new Fruit("banana");
        transactions.add(new FruitTransaction("b", banana, 100));
        transactions.add(new FruitTransaction("s", banana, 100));
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(banana, 200);
        transactionService.process(transactions);
        Map<Fruit, Integer> actual = FruitStorage.storage;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setEmptyTransaction_emptyDB_ok() {
        transactionService.process(Collections.emptyList());
        assertTrue(FruitStorage.storage.isEmpty());
    }
}

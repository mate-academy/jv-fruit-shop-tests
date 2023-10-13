package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.interfaces.FruitService;
import core.basesyntax.strategy.BalanceOperationStrategy;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitServiceImplTest {
    private FruitService fruitService;
    private Storage storage;
    private Map<String, OperationStrategy> operationStrategies;

    @Before
    public void setUp() {
        operationStrategies = new HashMap<>();
        operationStrategies.put("b", new BalanceOperationStrategy());
        operationStrategies.put("p", new PurchaseOperationStrategy());
        fruitService = new FruitServiceImpl(operationStrategies);
        storage = Storage.getInstance();
    }

    @Test
    public void processTransactions_BalanceAndPurchaseOperations_ok() {
        FruitTransaction transaction1 = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",
                100
        );
        FruitTransaction transaction2 = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple",
                50
        );
        List<FruitTransaction> transactions = List.of(transaction1, transaction2);
        fruitService.processTransactions(transactions);
        assertEquals(Integer.valueOf(100), storage.getFruitInventory().get("banana"));
        assertEquals(Integer.valueOf(-50), storage.getFruitInventory().get("apple"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void processTransactions_invalidOperationType() {
        FruitTransaction transaction = new FruitTransaction(null, "banana", 100);
        List<FruitTransaction> transactions = List.of(transaction);
        fruitService.processTransactions(transactions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void processTransactions_missingStrategy() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "orange", 50);
        operationStrategies.clear();
        List<FruitTransaction> transactions = List.of(transaction);
        fruitService.processTransactions(transactions);
    }
}

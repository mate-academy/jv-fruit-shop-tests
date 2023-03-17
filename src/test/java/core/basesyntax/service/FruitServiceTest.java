package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.storage.ProductStorage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategy;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitServiceTest {
    private static FruitService fruitService;
    private static List<FruitTransaction> fruitTransactions;

    @BeforeClass
    public static void beforeClass() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        OperationHandlerStrategy operationStrategy =
                new OperationHandlerStrategy(handlers);
        fruitService = new FruitServiceImpl(operationStrategy);
        fruitTransactions = new ArrayList<>();
    }

    @After
    public void clearData() {
        fruitTransactions.clear();
    }

    @Test(expected = RuntimeException.class)
    public void handleTransactions_nullTransactions_NotOk() {
        fruitService.handleTransactions(null);
        fail("An error was expected in case of null fruit transactions");
    }

    @Test(expected = RuntimeException.class)
    public void handleTransactions_emptyTransactions_NotOk() {
        fruitService.handleTransactions(fruitTransactions);
        fail("An error was expected in case of empty fruit transactions");
    }

    @Test(expected = RuntimeException.class)
    public void handleTransactions_wrongTransactions_NotOk() {
        fruitTransactions.add(new FruitTransaction(null, "banana", 20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", -10));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                null, 40));
        fruitService.handleTransactions(fruitTransactions);
        fail("An error was expected in case wrong transactions data");
    }

    @Test(expected = RuntimeException.class)
    public void handleTransactions_gettingMoreThanExists_NotOk() {
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 10));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 11));
        fruitService.handleTransactions(fruitTransactions);
        fail("An error was expected in case of getting more products than exists");
    }

    @Test
    public void handleTransactions_correctTransactions_Ok() {
        ProductStorage.products.clear();
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 10));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "orange", 20));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 30));
        fruitTransactions.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "orange", 10));
        fruitService.handleTransactions(fruitTransactions);
        Map<String, Integer> actual = ProductStorage.products;
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 40);
        expected.put("orange", 10);
        assertEquals(actual, expected);
    }
}

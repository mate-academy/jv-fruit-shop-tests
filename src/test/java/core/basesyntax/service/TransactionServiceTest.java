package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.activity.BalanceTransactionHandler;
import core.basesyntax.service.activity.PurchaseTransactionHandler;
import core.basesyntax.service.activity.ReturnTransactionHandler;
import core.basesyntax.service.activity.SupplyTransactionHandler;
import core.basesyntax.service.activity.TransactionHandler;
import core.basesyntax.strategy.ActivityStrategy;
import core.basesyntax.strategy.ActivityStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionServiceTest {
    private static TransactionService transactionService;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @BeforeAll
    static void beforeAll() {
        FruitDaoImpl fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, TransactionHandler> activityHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceTransactionHandler(fruitDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseTransactionHandler(fruitDao),
                FruitTransaction.Operation.RETURN, new ReturnTransactionHandler(fruitDao),
                FruitTransaction.Operation.SUPPLY, new SupplyTransactionHandler(fruitDao));
        ActivityStrategy activityStrategy = new ActivityStrategyImpl(activityHandlerMap);
        transactionService = new TransactionService(activityStrategy, fruitDao);
    }

    @Test
    void executeTransactions_nullFruitTransactionList_ok() {
        assertThrows(NullPointerException.class,
                () -> transactionService.executeTransactions(null));
    }

    @Test
    void executeTransactions_normalValues_ok() {
        List<FruitTransaction> fruitTransactionList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 50));
        transactionService.executeTransactions(fruitTransactionList);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(BANANA, 152);
        expected.put(APPLE, 90);
        assertEquals(expected, Storage.fruits);
    }

    @AfterAll
    static void afterAll() {
        Storage.fruits.clear();
    }
}

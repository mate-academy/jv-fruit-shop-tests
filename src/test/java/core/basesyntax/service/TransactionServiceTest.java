package core.basesyntax.service;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionServiceTest {
    private static TransactionService transactionService;

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
        Assertions.assertThrows(NullPointerException.class,
                () -> transactionService.executeTransactions(null));
    }

    @Test
    void executeTransactions_normalValues_ok() {
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 100));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 100));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 13));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.RETURN,
                "apple", 10));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 20));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 5));
        fruitTransactionList.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 50));
        transactionService.executeTransactions(fruitTransactionList);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 152);
        expected.put("apple", 90);
        Assertions.assertEquals(expected, Storage.fruits);
    }
}

package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceHandlerImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseHandlerImpl;
import core.basesyntax.service.operation.ReturnHandlerImpl;
import core.basesyntax.service.operation.SupplyHandlerImpl;
import core.basesyntax.strategy.TransactionStrategy;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionHandlerImplTest {
    private static final Map<FruitTransaction.Operation,
            OperationHandler> handlersMap = new HashMap<>();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fillHandlersMap();
    }

    @Test
    public void handle_Ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fillFruitTransactionsList(fruitTransactions);
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(handlersMap);
        TransactionHandler transactionHandler = new TransactionHandlerImpl(transactionStrategy);
        transactionHandler.handle(fruitTransactions);
        Map<String, Integer> expectedFruits = new HashMap<>();
        expectedFruits.put("banana", 152);
        expectedFruits.put("apple", 90);
        assertEquals(expectedFruits.size(), Storage.fruits.size());
        assertEquals(expectedFruits, Storage.fruits);
    }

    @Test(expected = NullPointerException.class)
    public void handle_nullInput_notOk() {
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(handlersMap);
        TransactionHandler transactionHandler = new TransactionHandlerImpl(transactionStrategy);
        transactionHandler.handle(null);
    }

    @Test
    public void handle_emptyInputList_ok() {
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(handlersMap);
        TransactionHandler transactionHandler = new TransactionHandlerImpl(transactionStrategy);
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        transactionHandler.handle(fruitTransactions);
        Map<String, Integer> expectedFruits = new HashMap<>();
        assertEquals(expectedFruits.size(), Storage.fruits.size());
        assertEquals(expectedFruits, Storage.fruits);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    private static void fillHandlersMap() {
        handlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
    }

    private void fillFruitTransactionsList(List<FruitTransaction> fruitTransactions) {
        FruitTransaction fruitTransaction1 = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("b"), 20);
        fruitTransactions.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = new FruitTransaction("apple",
                FruitTransaction.Operation.getByCode("b"), 100);
        fruitTransactions.add(fruitTransaction2);
        FruitTransaction fruitTransaction3 = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("s"), 100);
        fruitTransactions.add(fruitTransaction3);
        FruitTransaction fruitTransaction4 = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("p"), 13);
        fruitTransactions.add(fruitTransaction4);
        FruitTransaction fruitTransaction5 = new FruitTransaction("apple",
                FruitTransaction.Operation.getByCode("r"), 10);
        fruitTransactions.add(fruitTransaction5);
        FruitTransaction fruitTransaction6 = new FruitTransaction("apple",
                FruitTransaction.Operation.getByCode("p"), 20);
        fruitTransactions.add(fruitTransaction6);
        FruitTransaction fruitTransaction7 = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("p"), 5);
        fruitTransactions.add(fruitTransaction7);
        FruitTransaction fruitTransaction8 = new FruitTransaction("banana",
                FruitTransaction.Operation.getByCode("s"), 50);
        fruitTransactions.add(fruitTransaction8);
    }
}

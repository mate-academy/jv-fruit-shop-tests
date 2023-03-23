package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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
import org.junit.Before;
import org.junit.Test;

public class TransactionHandlerImplTest {
    private static List<FruitTransaction> fruitTransactions;
    private static Map<String, Integer> expectedFruits;
    private static TransactionHandler transactionHandler;
    private static final Map<FruitTransaction.Operation,
            OperationHandler> handlersMap = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        fruitTransactions = new ArrayList<>();
        expectedFruits = new HashMap<>();
        handlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(handlersMap);
        transactionHandler = new TransactionHandlerImpl(transactionStrategy);
    }

    @Test
    public void handle_Ok() {
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
        transactionHandler.handle(fruitTransactions);
        expectedFruits.put("banana", 152);
        expectedFruits.put("apple", 90);
        assertEquals(expectedFruits.size(), Storage.fruits.size());
        assertEquals(expectedFruits, Storage.fruits);
    }

    @Test
    public void handle_nullInput_notOk() {
        assertThrows(RuntimeException.class, () -> transactionHandler.handle(null));
    }

    @Test
    public void handle_emptyInputList_ok() {
        transactionHandler.handle(fruitTransactions);
        assertEquals(expectedFruits.size(), Storage.fruits.size());
        assertEquals(expectedFruits, Storage.fruits);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
        expectedFruits.clear();
    }
}

package core.service.impl;

import static org.junit.Assert.assertEquals;
import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.service.TransactionStrategy;
import core.basesyntax.service.impl.TransactionHandlerImpl;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import core.basesyntax.strategy.operation.BalanceHandlerImpl;
import core.basesyntax.strategy.operation.PurchaseHandlerImpl;
import core.basesyntax.strategy.operation.ReturnHandlerImpl;
import core.basesyntax.strategy.operation.SupplyHandlerImpl;
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

    public void expectedList(List<FruitTransaction> fruitTransactionsList) {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.getByCode("b"),
                "banana", 17
        );
        fruitTransactionsList.add(fruitTransaction);
        FruitTransaction fruitTransaction2 = new FruitTransaction(
                FruitTransaction.Operation.getByCode("s"),
                "apple", 24
        );
        fruitTransactionsList.add(fruitTransaction2);
    }

    @Test
    public void handle_Ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        expectedList(fruitTransactions);
        TransactionStrategy transactionStrategy = new TransactionStrategyImpl(handlersMap);
        TransactionHandler transactionHandler = new TransactionHandlerImpl(transactionStrategy);
        transactionHandler.handle(fruitTransactions);
        Map<String, Integer> expectedFruits = new HashMap<>();
        expectedFruits.put("banana", 17);
        expectedFruits.put("apple", 24);
        assertEquals(expectedFruits.size(), Storage.storage.size());
        assertEquals(expectedFruits, Storage.storage);
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
        assertEquals(expectedFruits.size(), Storage.storage.size());
        assertEquals(expectedFruits, Storage.storage);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }

    public static void fillHandlersMap() {
        handlersMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
        handlersMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
    }
}

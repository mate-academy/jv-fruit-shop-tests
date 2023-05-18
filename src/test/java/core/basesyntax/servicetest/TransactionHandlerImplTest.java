package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.service.serviceimpl.TransactionHandlerImpl;
import core.basesyntax.service.serviceimpl.operationhandlers.BalanceHandlerImpl;
import core.basesyntax.service.serviceimpl.operationhandlers.OperationHandler;
import core.basesyntax.service.serviceimpl.operationhandlers.PurchaseHandlerImpl;
import core.basesyntax.service.serviceimpl.operationhandlers.ReturnHandlerImpl;
import core.basesyntax.service.serviceimpl.operationhandlers.SupplyHandlerImpl;
import core.basesyntax.strategy.TransactionStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionHandlerImplTest {
    public static final int EXPECTED_EMPTY_MAP_SIZE = 0;
    public static final Map
            <FruitTransaction.Operation, OperationHandler> mapToHandle = new HashMap<>();

    @BeforeAll
    static void beforeAll() {
        fillMapToHandle();
    }

    @Test
    void handle_Ok() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fillTransactionsList(fruitTransactions);
        TransactionStrategyImpl transactionStrategy = new TransactionStrategyImpl(mapToHandle);
        TransactionHandler transactionHandler = new TransactionHandlerImpl(transactionStrategy);
        transactionHandler.handle(fruitTransactions);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 187);
        expected.put("apple", 1100);
        expected.put("orange", 2302);
        int actual = Storage.fruitsAndAmount.size();
        assertEquals(expected.size(), actual);
        assertEquals(expected, Storage.fruitsAndAmount);
    }

    @Test
    void handleEmptyList_Ok() {
        TransactionStrategyImpl transactionStrategy = new TransactionStrategyImpl(mapToHandle);
        TransactionHandler transactionHandler = new TransactionHandlerImpl(transactionStrategy);
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        transactionHandler.handle(fruitTransactions);
        Map<String, Integer> expected = new HashMap<>();
        int actual = Storage.fruitsAndAmount.size();
        assertEquals(EXPECTED_EMPTY_MAP_SIZE, actual);
        assertEquals(expected, Storage.fruitsAndAmount);
    }

    @Test
    void handleNull_NotOk() {
        TransactionStrategyImpl transactionStrategy = new TransactionStrategyImpl(mapToHandle);
        TransactionHandler transactionHandler = new TransactionHandlerImpl(transactionStrategy);
        assertThrows(NullPointerException.class, () -> transactionHandler.handle(null));
    }

    private void fillTransactionsList(List<FruitTransaction> list) {
        FruitTransaction fruitTransaction1 = new FruitTransaction(
                FruitTransaction.Operation.getCode("b"), "banana", 50);
        FruitTransaction fruitTransaction2 = new FruitTransaction(
                FruitTransaction.Operation.getCode("b"), "apple", 100);
        FruitTransaction fruitTransaction3 = new FruitTransaction(
                FruitTransaction.Operation.getCode("b"), "orange", 100);
        FruitTransaction fruitTransaction4 = new FruitTransaction(
                FruitTransaction.Operation.getCode("s"), "banana", 150);
        FruitTransaction fruitTransaction5 = new FruitTransaction(
                FruitTransaction.Operation.getCode("s"), "apple", 1000);
        FruitTransaction fruitTransaction6 = new FruitTransaction(
                FruitTransaction.Operation.getCode("s"), "orange", 2200);
        FruitTransaction fruitTransaction7 = new FruitTransaction(
                FruitTransaction.Operation.getCode("p"), "banana", 13);
        FruitTransaction fruitTransaction8 = new FruitTransaction(
                FruitTransaction.Operation.getCode("r"), "orange", 2);

        list.add(fruitTransaction1);
        list.add(fruitTransaction2);
        list.add(fruitTransaction3);
        list.add(fruitTransaction4);
        list.add(fruitTransaction5);
        list.add(fruitTransaction6);
        list.add(fruitTransaction7);
        list.add(fruitTransaction8);
    }

    private static void fillMapToHandle() {
        mapToHandle.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        mapToHandle.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
        mapToHandle.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
        mapToHandle.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());
    }
}

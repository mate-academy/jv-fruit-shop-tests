package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import db.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ShopService;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.impl.BalanceOperationHandler;
import strategy.impl.OperationStrategyImpl;

class ShopServiceImplTest {
    private Storage storage;
    private OperationStrategy operationStrategy;
    private ShopService service;
    private List<FruitTransaction> transactions;

    @BeforeEach
    void setUp() {
        transactions = new ArrayList<>();
        storage = new Storage();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap
                = new HashMap<>();
        handlerMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationStrategy = new OperationStrategyImpl(handlerMap);
        service = new ShopServiceImpl(storage, operationStrategy);
    }

    @Test
    void process_listIsNull_NotOk() {
        assertThrows(RuntimeException.class, 
                () -> service.process(null));
    }

    @Test
    void process_transactionIsNull_NotOk() {
        transactions.add(null);
        assertThrows(RuntimeException.class,
                () -> service.process(transactions));
    }

    @Test
    void process_transactionOperationIsNull_NotOk() {
        FruitTransaction transaction = new FruitTransaction(
                null, "apple", 11);
        transactions.add(transaction);
        assertThrows(RuntimeException.class,
                () -> service.process(transactions));
    }

    @Test
    void process_singleValidValue_Ok() {
        transactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 15));
        service.process(transactions);
        assertEquals(15, storage.getAll().get("apple"));
    }

    @Test
    void process_twoOrMoreValidValue_Ok() {
        transactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 15));
        transactions.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 31));
        service.process(transactions);
        assertEquals(15, storage.getAll().get("apple"));
        assertEquals(31, storage.getAll().get("banana"));
    }

    @Test
    void process_incorrectOperationHandler_NotOk() {
        transactions.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 3
        ));
        assertThrows(RuntimeException.class,
                () -> service.process(transactions));
    }
}

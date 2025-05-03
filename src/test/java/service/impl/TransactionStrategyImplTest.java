package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TransactionStrategy;
import service.activities.BalanceTransactionHandler;
import service.activities.SupplyTransactionHandler;
import service.activities.TransactionHandler;

class TransactionStrategyImplTest {
    private Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap;
    private TransactionStrategy transactionStrategy;

    @BeforeEach
    void setUp() {
        transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());

        transactionStrategy = new TransactionStrategyImpl(transactionHandlerMap);
    }

    @Test
    void getHandler_balance_Ok() {
        TransactionHandler handler = transactionStrategy
                .getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceTransactionHandler.class, handler.getClass());
    }

    @Test
    void getHandler_balance_NotOk() {
        TransactionHandler handler = transactionStrategy
                .getHandler(FruitTransaction.Operation.BALANCE);
        assertNotEquals(SupplyTransactionHandler.class, handler.getClass());
    }
}

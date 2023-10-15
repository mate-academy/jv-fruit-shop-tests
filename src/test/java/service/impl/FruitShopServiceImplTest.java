package service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FruitShopService;
import service.TransactionStrategy;
import service.activities.BalanceTransactionHandler;
import service.activities.PurchaseTransactionHandler;
import service.activities.ReturnTransactionHandler;
import service.activities.SupplyTransactionHandler;
import service.activities.TransactionHandler;

class FruitShopServiceImplTest {
    private Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap;
    private TransactionStrategy transactionStrategy;
    private FruitShopService fruitShopService;
    private List<FruitTransaction> fruitTransactionList;

    @BeforeEach
    void setUp() {
        transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandler());
        transactionHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());

        transactionStrategy = new TransactionStrategyImpl(transactionHandlerMap);
        fruitShopService = new FruitShopServiceImpl(transactionStrategy);

        fruitTransactionList = new ArrayList<>();

    }

    @Test
    void handleTransactions_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(74);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionList.add(fruitTransaction);
        fruitShopService.handleTransactions(fruitTransactionList);
        TransactionHandler handler = transactionStrategy
                .getHandler(FruitTransaction.Operation.BALANCE);
        assertTrue(handler instanceof BalanceTransactionHandler);
    }

    @Test
    void handleTransactions_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(74);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransactionList.add(fruitTransaction);
        fruitShopService.handleTransactions(fruitTransactionList);
        TransactionHandler handler = transactionStrategy
                .getHandler(FruitTransaction.Operation.BALANCE);
        assertFalse(handler instanceof SupplyTransactionHandler);
    }
}

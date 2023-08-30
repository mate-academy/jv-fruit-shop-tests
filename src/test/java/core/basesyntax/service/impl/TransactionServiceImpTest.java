package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.service.handler.BalanceOperationHandler;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.PurchaseOperationHandler;
import core.basesyntax.service.handler.ReturnOperationHandler;
import core.basesyntax.service.handler.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionServiceImpTest {
    private static final int FINAL_VALUE_BANANA = 120;
    private static final int FINAL_VALUE_APPLE = 10;
    private static TransactionService transactionService;
    private static List<FruitTransaction> fruitTransactionsList;
    private static List<FruitTransaction> fruitTransactionsListEmpty;
    private static FruitTransaction fruitTransactionBalance;
    private static FruitTransaction fruitTransactionReturn;
    private static FruitTransaction fruitTransactionSupply;
    private static FruitTransaction fruitTransactionPurchase;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        transactionService
                = new TransactionServiceImp(new OperationStrategyImpl(operationHandlerMap));
        fruitTransactionsListEmpty = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        fruitTransactionBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20);
        fruitTransactionReturn =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",20);
        fruitTransactionSupply =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"banana",100);
        fruitTransactionPurchase =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple",10);

        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        fruitTransactionsList
                = List.of(fruitTransactionBalance,fruitTransactionReturn,
                fruitTransactionSupply,fruitTransactionPurchase);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void processTransaction_listIsEmpty_Ok() {
        transactionService.processTransaction(fruitTransactionsListEmpty);
        assertTrue(Storage.storage.isEmpty());
    }

    @Test
    void processTransaction_listIsNotEmpty_Ok() {
        transactionService.processTransaction(fruitTransactionsList);
        assertEquals(Storage.storage.get("banana"),FINAL_VALUE_BANANA);
        assertEquals(Storage.storage.get("apple"),FINAL_VALUE_APPLE);
    }

    @Test
    void processTransaction_listIsNull_NotOk() {
        assertThrows(RuntimeException.class, () -> transactionService.processTransaction(null));
    }
}

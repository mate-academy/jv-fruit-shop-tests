package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import database.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ShopService;
import strategy.BalanceOperation;
import strategy.OperationHandler;
import strategy.OperationStrategy;
import strategy.OperationStrategyImpl;
import strategy.PurchaseOperation;
import strategy.ReturnOperation;
import strategy.SupplyOperation;

class ShopServiceTest {
    private static Map<FruitTransaction.Operation, OperationHandler> handlers;
    private static Map<String, Integer> expectedStorage;
    private static List<FruitTransaction> transactionList;
    private OperationStrategy operationStrategy;
    private ShopService shopService;
    private FruitTransaction balanceTransaction;
    private FruitTransaction purchaseTransaction;
    private FruitTransaction supplyTransaction;
    private FruitTransaction returnTransaction;
    private FruitTransaction provokingTransaction;

    @BeforeAll
    static void beforeAll() {
        handlers = Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation());
        expectedStorage = new HashMap<>();
        transactionList = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
        balanceTransaction = new FruitTransaction("b", "grape", 12);
        purchaseTransaction = new FruitTransaction("p", "grape", 5);
        supplyTransaction = new FruitTransaction("s", "grape", 9);
        returnTransaction = new FruitTransaction("r", "grape", 3);
        provokingTransaction = new FruitTransaction("p", "grape", 100);
        transactionList.add(balanceTransaction);
        transactionList.add(purchaseTransaction);
        transactionList.add(supplyTransaction);
        transactionList.add(returnTransaction);
    }

    @AfterEach
    void tearDown() {
        expectedStorage.clear();
        Storage.clearStorage();
        transactionList.clear();
    }

    @Test
    void process_sequenceOfTransactions_ok() {
        expectedStorage.put("grape", 19);

        shopService.process(transactionList);
        Map<String, Integer> actualStorage = Storage.getAssortment();

        assertEquals(expectedStorage, actualStorage);
    }

    @Test
    void process_provokingTransaction_notOk() {
        transactionList.add(provokingTransaction);
        Throwable exception = assertThrows(RuntimeException.class,
                () -> shopService.process(transactionList));
        assertEquals("Negative balance for fruit: grape with quantity: -100",
                exception.getMessage());
    }
}

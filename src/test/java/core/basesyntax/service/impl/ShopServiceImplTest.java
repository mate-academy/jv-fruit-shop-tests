package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.StorageService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private StorageService storageService;
    private ShopService shopService;
    private OperationHandler balanceHandler;
    private OperationHandler supplyHandler;
    private OperationHandler purchaseHandler;
    private OperationHandler returnHandler;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
        balanceHandler = new BalanceOperation(storageService);
        supplyHandler = new SupplyOperation(storageService);
        purchaseHandler = new PurchaseOperation(storageService);
        returnHandler = new ReturnOperation(storageService);

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        operationHandlers.put(FruitTransaction.Operation.RETURN, returnHandler);

        shopService = new ShopServiceImpl(operationHandlers);

        storageService = new StorageServiceImpl();
        storageService.clear();
    }

    @Test
    void testProcessWithValidTransactions() {
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100
        );

        FruitTransaction supplyTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 50
        );

        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction, supplyTransaction);

        shopService.process(transactions);

        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(100, fruits.get("apple"));
        assertEquals(50, fruits.get("banana"));
    }

    @Test
    void testProcessWithInvalidTransaction() {
        FruitTransaction invalidTransaction = new FruitTransaction(
                null, "orange", 30
        );

        List<FruitTransaction> transactions = Arrays.asList(invalidTransaction);

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }

    @Test
    void testProcessWithMixedTransactions() {
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100
        );

        FruitTransaction invalidTransaction = new FruitTransaction(
                null, "orange", 30
        );

        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction, invalidTransaction);

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));

        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(100, fruits.get("apple"));
        assertNull(fruits.get("orange"));
    }

    @Test
    void testProcessWithPurchaseTransaction() {
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100
        );

        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 30
        );

        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction,
                purchaseTransaction);

        shopService.process(transactions);

        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(70, fruits.get("apple"));
    }

    @Test
    void testProcessWithReturnTransaction() {
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100
        );

        FruitTransaction returnTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 30
        );

        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction, returnTransaction);

        shopService.process(transactions);

        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(130, fruits.get("apple"));
    }
}

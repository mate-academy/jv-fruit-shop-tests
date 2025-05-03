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
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_ORANGE = "orange";
    private static final int QUANTITY_INITIAL_APPLE = 100;
    private static final int QUANTITY_INITIAL_BANANA = 50;
    private static final int QUANTITY_INITIAL_ORANGE = 30;
    private static final int QUANTITY_PURCHASE_APPLE = 30;
    private static final int QUANTITY_RETURN_APPLE = 30;
    private static final int QUANTITY_FINAL_APPLE_AFTER_PURCHASE = 70;
    private static final int QUANTITY_FINAL_APPLE_AFTER_RETURN = 130;

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

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, balanceHandler,
                FruitTransaction.Operation.SUPPLY, supplyHandler,
                FruitTransaction.Operation.PURCHASE, purchaseHandler,
                FruitTransaction.Operation.RETURN, returnHandler
        );

        shopService = new ShopServiceImpl(operationHandlers);

        storageService = new StorageServiceImpl();
        storageService.clear();
    }

    @Test
    void testProcessWithValidTransactions() {
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_APPLE, QUANTITY_INITIAL_APPLE
        );

        FruitTransaction supplyTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, FRUIT_BANANA, QUANTITY_INITIAL_BANANA
        );

        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction, supplyTransaction);

        shopService.process(transactions);

        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(QUANTITY_INITIAL_APPLE, fruits.get(FRUIT_APPLE));
        assertEquals(QUANTITY_INITIAL_BANANA, fruits.get(FRUIT_BANANA));
    }

    @Test
    void testProcessWithInvalidTransaction() {
        FruitTransaction invalidTransaction = new FruitTransaction(
                null, FRUIT_ORANGE, QUANTITY_INITIAL_ORANGE
        );

        List<FruitTransaction> transactions = Arrays.asList(invalidTransaction);

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));
    }

    @Test
    void testProcessWithMixedTransactions() {
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_APPLE, QUANTITY_INITIAL_APPLE
        );

        FruitTransaction invalidTransaction = new FruitTransaction(
                null, FRUIT_ORANGE, QUANTITY_INITIAL_ORANGE
        );

        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction, invalidTransaction);

        assertThrows(RuntimeException.class, () -> shopService.process(transactions));

        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(QUANTITY_INITIAL_APPLE, fruits.get(FRUIT_APPLE));
        assertNull(fruits.get(FRUIT_ORANGE));
    }

    @Test
    void testProcessWithPurchaseTransaction() {
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_APPLE, QUANTITY_INITIAL_APPLE
        );

        FruitTransaction purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_APPLE, QUANTITY_PURCHASE_APPLE
        );

        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction,
                purchaseTransaction);

        shopService.process(transactions);

        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(QUANTITY_FINAL_APPLE_AFTER_PURCHASE, fruits.get(FRUIT_APPLE));
    }

    @Test
    void testProcessWithReturnTransaction() {
        FruitTransaction balanceTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_APPLE, QUANTITY_INITIAL_APPLE
        );

        FruitTransaction returnTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, FRUIT_APPLE, QUANTITY_RETURN_APPLE
        );

        List<FruitTransaction> transactions = Arrays.asList(balanceTransaction, returnTransaction);

        shopService.process(transactions);

        Map<String, Integer> fruits = storageService.getAllFruits();
        assertEquals(QUANTITY_FINAL_APPLE_AFTER_RETURN, fruits.get(FRUIT_APPLE));
    }
}

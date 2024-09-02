package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.impl.StorageServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 10;
    private static final int ANOTHER_APPLE_QUANTITY = 20;
    private static final String BANANA = "banana";
    private static final int BANANA_QUANTITY = 10;
    private ShopServiceImpl shopService;
    private StorageService storageService;
    private List<FruitTransaction> transactions;

    @BeforeEach
    void setUp() {
        Storage.getAllFruits();
        storageService = new StorageServiceImpl();

        OperationHandler supplyHandler = new SupplyOperation(storageService);
        OperationHandler purchaseHandler = new PurchaseOperation(storageService);
        OperationHandler returnHandler = new ReturnOperation(storageService);
        OperationHandler balanceHandler = new BalanceOperation(storageService);

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        handlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        handlers.put(FruitTransaction.Operation.RETURN, returnHandler);
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        OperationStrategy operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);

        transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 60),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 30),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 10),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 20)
        );
    }

    @Test
    void process_validTransaction_ok() {
        Map<String, Integer> expected = Map.of(
                BANANA, 20,
                APPLE, 80
        );
        shopService.process(transactions);
        assertEquals(expected, storageService.getAllFruits());
    }

    @Test
    void getStorage_emptyStorage_ok() {
        Map<String, Integer> storage = shopService.getStorage();
        assertTrue(storage.isEmpty());
    }

    @Test
    void testEquals_sameObject_ok() {
        FruitTransaction transaction
                = new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        assertTrue(transaction.equals(transaction));
    }

    @Test
    void testEquals_differentType_notOk() {
        FruitTransaction transaction
                = new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        String otherObject = BANANA;
        assertFalse(transaction.equals(otherObject));
    }

    @Test
    void testEquals_null_notOk() {
        FruitTransaction transaction
                = new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        assertFalse(transaction.equals(null));
    }

    @Test
    void testHashCode_equalObjects_ok() {
        FruitTransaction transaction1
                = new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        FruitTransaction transaction2
                = new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    void testHashCode_differentOperation_notOk() {
        FruitTransaction transaction1
                = new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        FruitTransaction transaction2
                = new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY, APPLE, APPLE_QUANTITY);
        assertNotEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    void testHashCode_differentFruit_notOk() {
        FruitTransaction transaction1
                = new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        FruitTransaction transaction2
                = new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, BANANA, BANANA_QUANTITY);
        assertNotEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    void testHashCode_differentQuantity_notOk() {
        FruitTransaction transaction1
                = new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE, APPLE_QUANTITY);
        FruitTransaction transaction2
                = new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE, ANOTHER_APPLE_QUANTITY);
        assertNotEquals(transaction1.hashCode(), transaction2.hashCode());
    }
}

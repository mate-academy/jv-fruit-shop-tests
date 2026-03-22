package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.ShopStorage;
import core.basesyntax.model.ShopTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.ShopOperationHandler;
import core.basesyntax.strategy.impl.ShopBalanceOperationHandler;
import core.basesyntax.strategy.impl.ShopOperationStrategy;
import core.basesyntax.strategy.impl.ShopPurchaseOperationHandler;
import core.basesyntax.strategy.impl.ShopReturnOperationHandler;
import core.basesyntax.strategy.impl.ShopSupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DefaultShopServiceTest {
    private static ShopService service;
    private static Storage storage;
    private static Map<ShopTransaction.Operation, ShopOperationHandler> operationHandlers;
    private final List<ShopTransaction> testShopTransactions = new ArrayList<>();

    @BeforeAll
    static void init() {
        operationHandlers = new HashMap<>();
        storage = new ShopStorage();

        operationHandlers.put(ShopTransaction.Operation.BALANCE, new ShopBalanceOperationHandler());
        operationHandlers.put(ShopTransaction.Operation.SUPPLY, new ShopSupplyOperationHandler());
        operationHandlers.put(ShopTransaction.Operation.PURCHASE,
                new ShopPurchaseOperationHandler());
        operationHandlers.put(ShopTransaction.Operation.RETURN, new ShopReturnOperationHandler());

        service = new DefaultShopService(storage, new ShopOperationStrategy(operationHandlers));
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void constructor_validArguments_ok() {
        assertDoesNotThrow(() -> new DefaultShopService(
                storage,
                new ShopOperationStrategy(operationHandlers))
        );
    }

    @Test
    void process_validCase_updates_ok() {
        testShopTransactions.add(
                new ShopTransaction(ShopTransaction.Operation.BALANCE, "banana", 20));
        testShopTransactions.add(
                new ShopTransaction(ShopTransaction.Operation.BALANCE, "apple", 100));
        testShopTransactions.add(
                new ShopTransaction(ShopTransaction.Operation.SUPPLY, "banana", 100));
        testShopTransactions.add(
                new ShopTransaction(ShopTransaction.Operation.PURCHASE, "banana", 13));
        testShopTransactions.add(
                new ShopTransaction(ShopTransaction.Operation.RETURN, "apple", 10));
        testShopTransactions.add(
                new ShopTransaction(ShopTransaction.Operation.PURCHASE, "apple", 20));
        testShopTransactions.add(
                new ShopTransaction(ShopTransaction.Operation.PURCHASE, "banana", 5));
        testShopTransactions.add(
                new ShopTransaction(ShopTransaction.Operation.SUPPLY, "banana", 50));

        service.process(testShopTransactions);

        Map<String, Integer> expected = Map.of("banana", 152, "apple", 90);
        Map<String, Integer> actual = storage.getAll();

        assertEquals(expected, actual, "service.process() does not process transactions right");
    }
}

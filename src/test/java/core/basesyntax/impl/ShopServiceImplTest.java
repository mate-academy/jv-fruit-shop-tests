package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StorageService;
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
    private static final String BANANA = "banana";
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
}

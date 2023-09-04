package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.implementation.StorageServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StrategyImplementationTest {
    private final Map<FruitTransaction.Operation, FruitShopOperationsHandler> processSelectorTest
            = new HashMap<>();

    @BeforeEach
    void setUp() {
        StorageService storageService = new StorageServiceImpl();
        processSelectorTest.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(storageService));
        processSelectorTest.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(storageService));
        processSelectorTest.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler(storageService));
        processSelectorTest.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(storageService));
    }

    @Test
    void getProcessSelector_validData_ok() {
        StrategyImplementation strategyImplementation
                = new StrategyImplementation(processSelectorTest);
        FruitTransaction transactionTest = new FruitTransaction();
        transactionTest.setOperation(FruitTransaction.Operation.SUPPLY);
        transactionTest.setFruit("banana");
        transactionTest.setQuantity(100);
        FruitShopOperationsHandler handler
                = strategyImplementation.getProcessSelector().get(transactionTest.getOperation());
        assertTrue(handler.getClass().equals(SupplyHandler.class));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }
}

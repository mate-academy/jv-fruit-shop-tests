package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.StorageService;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.FruitShopOperationsHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private FruitTransaction transaction;
    private StorageService storageService;
    private Map<FruitTransaction.Operation, FruitShopOperationsHandler> processSelector
            = new HashMap<>();

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl();
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(20);
        storageService = new StorageServiceImpl();
        processSelector.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(storageService));
        processSelector.put(FruitTransaction.Operation.BALANCE, new BalanceHandler(storageService));
        processSelector.put(FruitTransaction.Operation.RETURN, new ReturnHandler(storageService));
        processSelector.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(storageService));
    }

    @Test
    void process_validTransaction_ok() {
        shopService.process(transaction, processSelector);
        assertEquals(transaction.getQuantity(), Storage.STORAGE.get("banana"));
    }

    @Test
    void process_nullTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> shopService.process(null, processSelector));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }
}

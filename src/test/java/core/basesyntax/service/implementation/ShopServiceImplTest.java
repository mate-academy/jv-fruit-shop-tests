package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.StorageService;
import core.basesyntax.strategy.FruitShopOperationsHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String PRODUCT_NAME = "apricot";
    private static final int POSITIVE_QUANTITY = 15;
    private static ShopService shopService;
    private static Map<FruitTransaction.Operation, FruitShopOperationsHandler> processSelector;
    private static FruitTransaction transaction;
    private static StorageService storageService;

    @BeforeAll
    static void beforeAll() {
        shopService = new ShopServiceImpl();
        storageService = new StorageServiceImpl();
        processSelector = new HashMap<>();
        processSelector.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(storageService));
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit(PRODUCT_NAME);
        transaction.setQuantity(POSITIVE_QUANTITY);
    }

    @Test
    void process_validTransaction_ok() {
        shopService.process(transaction, processSelector);
        assertEquals(transaction.getQuantity(), Storage.STORAGE.get(PRODUCT_NAME));
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

package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.implementation.StorageServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String PRODUCT_NAME = "apricot";
    private static final int POSITIVE_QUANTITY = 15;
    private static final int TOO_LARGE_QUANTITY = 100;
    private static final int ZERO_QUANTITY = 0;
    private static final int NEGATIVE_QUANTITY = -1;
    private static final int STORAGE_BALANCE = 50;
    private FruitShopOperationsHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        StorageService storageService = new StorageServiceImpl();
        purchaseHandler = new PurchaseHandler(storageService);
        Storage.STORAGE.put(PRODUCT_NAME, STORAGE_BALANCE);
    }

    @Test
    void applyOperation_validData_ok() {
        purchaseHandler.applyOperation(PRODUCT_NAME, POSITIVE_QUANTITY);
        assertTrue(Storage.STORAGE.containsKey(PRODUCT_NAME));
        assertEquals(Storage.STORAGE.get(PRODUCT_NAME), STORAGE_BALANCE - POSITIVE_QUANTITY);
    }

    @Test
    void applyOperation_insufficientQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.applyOperation(PRODUCT_NAME,
                        STORAGE_BALANCE - TOO_LARGE_QUANTITY));
    }

    @Test
    void applyOperation_zeroQuantity_ok() {
        purchaseHandler.applyOperation(PRODUCT_NAME, ZERO_QUANTITY);
        assertTrue(Storage.STORAGE.containsKey(PRODUCT_NAME));
        assertEquals(Storage.STORAGE.get(PRODUCT_NAME), STORAGE_BALANCE - ZERO_QUANTITY);
    }

    @Test
    void applyOperation_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.applyOperation(PRODUCT_NAME, NEGATIVE_QUANTITY));
    }

    @Test
    void applyOperation_keyNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.applyOperation(null, POSITIVE_QUANTITY));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }
}

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

class ReturnHandlerTest {
    private static final String PRODUCT_NAME = "apricot";
    private static final int POSITIVE_QUANTITY = 15;
    private static final int ZERO_QUANTITY = 0;
    private static final int NEGATIVE_QUANTITY = -1;
    private FruitShopOperationsHandler returnHandler;

    @BeforeEach
    void setUp() {
        StorageService storageService = new StorageServiceImpl();
        returnHandler = new SupplyHandler(storageService);
    }

    @Test
    void applyOperation_validData_ok() {
        returnHandler.applyOperation(PRODUCT_NAME, POSITIVE_QUANTITY);
        assertTrue(Storage.STORAGE.containsKey(PRODUCT_NAME));
        assertEquals(Storage.STORAGE.get(PRODUCT_NAME), POSITIVE_QUANTITY);
    }

    @Test
    void applyOperation_zeroQuantity_ok() {
        returnHandler.applyOperation(PRODUCT_NAME, ZERO_QUANTITY);
        assertTrue(Storage.STORAGE.containsKey(PRODUCT_NAME));
        assertEquals(Storage.STORAGE.get(PRODUCT_NAME), ZERO_QUANTITY);
    }

    @Test
    void applyOperation_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> returnHandler.applyOperation(PRODUCT_NAME, NEGATIVE_QUANTITY));
    }

    @Test
    void applyOperation_keyNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> returnHandler.applyOperation(null, POSITIVE_QUANTITY));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }
}

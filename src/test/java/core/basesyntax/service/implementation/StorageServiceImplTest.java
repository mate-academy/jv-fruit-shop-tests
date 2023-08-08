package core.basesyntax.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InsufficientStockException;
import core.basesyntax.exceptions.ProductIsAbsentException;
import core.basesyntax.service.StorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageServiceImplTest {
    private static final String PRODUCT_NAME = "apricot";
    private static final int POSITIVE_QUANTITY = 15;
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
    }

    @Test
    void add_validData_ok() {
        int expectedQuantity = 15;
        storageService.add(PRODUCT_NAME, POSITIVE_QUANTITY);
        assertEquals(expectedQuantity, Storage.STORAGE.get(PRODUCT_NAME));
    }

    @Test
    void add_notValidData_notOk() {
        assertThrows(RuntimeException.class, () -> storageService.add(null, POSITIVE_QUANTITY));
    }

    @Test
    void update_validData_ok() {
        int additionalQuantity = 5;
        storageService.add(PRODUCT_NAME, POSITIVE_QUANTITY);
        storageService.update(PRODUCT_NAME, additionalQuantity);
        assertEquals(POSITIVE_QUANTITY + additionalQuantity,
                Storage.STORAGE.get(PRODUCT_NAME));
    }

    @Test
    void update_notValidData_notOk() {
        assertThrows(RuntimeException.class, () -> storageService.update(null, POSITIVE_QUANTITY));
    }

    @Test
    void remove_validData_ok() {
        int extraQuantity = 15;
        storageService.add(PRODUCT_NAME, POSITIVE_QUANTITY);
        storageService.remove(PRODUCT_NAME, extraQuantity);
        assertEquals(POSITIVE_QUANTITY - extraQuantity, Storage.STORAGE.get(PRODUCT_NAME));
    }

    @Test
    void remove_notEnoughQuantity_notOk() {
        int largeExtraQuantity = 25;
        storageService.add(PRODUCT_NAME, POSITIVE_QUANTITY);
        assertThrows(InsufficientStockException.class,
                () -> storageService.remove(PRODUCT_NAME, largeExtraQuantity));
    }

    @Test
    void remove_notValidData_notOk() {
        storageService.add(PRODUCT_NAME, POSITIVE_QUANTITY);
        assertThrows(RuntimeException.class, () -> storageService.remove(null, POSITIVE_QUANTITY));
    }

    @Test
    void remove_noProductInStorage_notOk() {
        String missingProduct = "apple";
        storageService.add(PRODUCT_NAME, POSITIVE_QUANTITY);
        assertThrows(ProductIsAbsentException.class,
                () -> storageService.remove(missingProduct, POSITIVE_QUANTITY));
    }

    @Test
    void remove_negativeValue_notOk() {
        int negativeQuantity = -25;
        storageService.add(PRODUCT_NAME, POSITIVE_QUANTITY);
        assertThrows(RuntimeException.class,
                () -> storageService.remove(PRODUCT_NAME, negativeQuantity));
    }

    @Test
    void get_validData_ok() {
        storageService.add(PRODUCT_NAME, POSITIVE_QUANTITY);
        assertEquals(storageService.get(PRODUCT_NAME), Storage.STORAGE.get(PRODUCT_NAME));
    }

    @Test
    void get_notValidData_notOk() {
        assertThrows(RuntimeException.class, () -> storageService.get(null));
    }

    @AfterEach
    public void afterEachTest() {
        Storage.STORAGE.clear();
    }
}

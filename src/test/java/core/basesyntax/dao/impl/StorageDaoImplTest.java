package core.basesyntax.dao.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final int BANANA_VALID_QUANTITY = 152;
    private static final int APPLE_VALID_QUANTITY = 90;
    private static final int MANGO_VALID_QUANTITY = 90;
    private static final int INVALID_QUANTITY = -1;
    private static final String PRODUCT_BANANA = "banana";
    private static final String PRODUCT_APPLE = "apple";
    private static final String PRODUCT_MANGO = "mango";

    @BeforeEach
    void setUp() {
        Storage.fruits.putAll(Map.of(
                PRODUCT_BANANA, BANANA_VALID_QUANTITY,
                PRODUCT_APPLE, APPLE_VALID_QUANTITY
        ));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void getStorage_returnsMapOfProducts_ok() {
        Map<String, Integer> expected = Map.of(
                PRODUCT_BANANA, BANANA_VALID_QUANTITY,
                PRODUCT_APPLE, APPLE_VALID_QUANTITY
        );
        Map<String, Integer> actual = storageDao.getStorage();
        assertEquals(expected, actual);
    }

    @Test
    void putProduct_productParameterIsNull_notOk() {
        IllegalArgumentException expected = assertThrows(IllegalArgumentException.class,
                () -> storageDao.putProduct(null, BANANA_VALID_QUANTITY));
        assertEquals("Product is null or empty", expected.getMessage());
    }

    @Test
    void putProduct_productParameterIsEmpty_notOk() {
        IllegalArgumentException expected = assertThrows(IllegalArgumentException.class,
                () -> storageDao.putProduct("", BANANA_VALID_QUANTITY));
        assertEquals("Product is null or empty", expected.getMessage());
    }

    @Test
    void putProduct_quantityParameterIsLessThenZero_notOk() {
        IllegalArgumentException expected = assertThrows(IllegalArgumentException.class,
                () -> storageDao.putProduct(PRODUCT_MANGO, INVALID_QUANTITY));
        assertEquals("Quantity is less then 0", expected.getMessage());
    }

    @Test
    void putProduct_putProductToTheStorage_ok() {
        storageDao.putProduct(PRODUCT_MANGO, MANGO_VALID_QUANTITY);
        Integer expectedMangoQuantity = Storage.fruits.get(PRODUCT_MANGO);
        Integer expectedBananaQuantity = Storage.fruits.get(PRODUCT_BANANA);
        Integer expectedAppleQuantity = Storage.fruits.get(PRODUCT_APPLE);
        assertAll(() -> assertEquals(expectedMangoQuantity,
                        storageDao.getAmountByProductName(PRODUCT_MANGO)),
                () -> assertEquals(expectedBananaQuantity,
                        storageDao.getAmountByProductName(PRODUCT_BANANA)),
                () -> assertEquals(expectedAppleQuantity,
                        storageDao.getAmountByProductName(PRODUCT_APPLE)));
    }

    @Test
    void getAmountByProductName_productParameterIsNull_notOk() {
        IllegalArgumentException expected = assertThrows(IllegalArgumentException.class,
                () -> storageDao.putProduct(null, BANANA_VALID_QUANTITY));
        assertEquals("Product is null or empty", expected.getMessage());
    }

    @Test
    void getAmountByProductName_returnsProductQuantity_ok() {
        int actual = storageDao.getAmountByProductName(PRODUCT_BANANA);
        assertEquals(BANANA_VALID_QUANTITY, actual);
    }
}

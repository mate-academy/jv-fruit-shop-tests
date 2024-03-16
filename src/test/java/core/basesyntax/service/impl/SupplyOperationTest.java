package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Product;
import core.basesyntax.service.RecordDataManipulation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final Storage STORAGE = Storage.getInstance();
    private static final RecordDataManipulation OPERATION = new SupplyOperation();
    private static final String NAME = "test";
    private static final String NEGATIVE_PRODUCT_MSG = "Count cannot be negative. Product: ";
    private static final int COUNT = 12;
    private static final Product PRODUCT = new Fruit(NAME, COUNT);

    @Test
    void operate_EmptyStorageIsUpdatedWithNewProduct_ok() {
        OPERATION.operate(PRODUCT);
        Product expected = PRODUCT;
        Product actual = STORAGE.getStorage().get(NAME);
        assertEquals(expected, actual);
    }

    @Test
    void operate_StorageWithZeroCountProductIsUpdatedWithSameProductType_ok() {
        Product zeroCountProduct = new Fruit(NAME, 0);
        STORAGE.getStorage().put(NAME, zeroCountProduct);
        OPERATION.operate(PRODUCT);
        Product expected = PRODUCT;
        Product actual = STORAGE.getStorage().get(NAME);
        assertEquals(expected, actual);
    }

    @Test
    void operate_FilledStorageIsUpdatedWithSameProduct_Ok() {
        STORAGE.getStorage().put(NAME, PRODUCT);
        OPERATION.operate(PRODUCT);
        Product expected = new Fruit(NAME, COUNT * 2);
        Product actual = STORAGE.getStorage().get(NAME);
        assertEquals(expected, actual);
    }

    @Test
    void operate_ProductCountLessThenZero_notOk() {
        Product productCountLessThenZero = new Fruit(NAME, -1);
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> OPERATION.operate(productCountLessThenZero));
        String expected = NEGATIVE_PRODUCT_MSG + NAME;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        STORAGE.getStorage().clear();
    }
}

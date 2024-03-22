package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Product;
import core.basesyntax.service.RecordDataManipulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String AMOUNT_CANNOT_BE_NEGATIVE = "Count cannot be negative. Product: ";
    private static final String PRODUCT_NAME = "test";
    private static final int AMOUNT = 12;
    private final Product product = new Fruit(PRODUCT_NAME, AMOUNT);
    private final Storage storage = Storage.getInstance();
    private final RecordDataManipulation operation = new BalanceOperation();

    @BeforeEach
    void tearDown() {
        storage.getStorage().clear();
    }

    @Test
    void operate_EmptyStorageIsUpdatedWithNewProduct_ok() {
        operation.operate(product);

        Product actual = storage.getStorage().get(PRODUCT_NAME);
        Product expected = product;
        assertEquals(expected, actual);
    }

    @Test
    void operate_StorageWithZeroCountProductIsUpdatedWithSameProductType_ok() {
        Product zeroCountProduct = new Fruit(PRODUCT_NAME, 0);
        storage.getStorage().put(PRODUCT_NAME, zeroCountProduct);

        operation.operate(product);

        Product actual = storage.getStorage().get(PRODUCT_NAME);
        Product expected = product;
        assertEquals(expected, actual);
    }

    @Test
    void operate_FilledStorageIsUpdatedWithSameProduct_Ok() {
        storage.getStorage().put(PRODUCT_NAME, product);

        operation.operate(product);

        Product actual = storage.getStorage().get(PRODUCT_NAME);
        Product expected = new Fruit(PRODUCT_NAME, AMOUNT * 2);
        assertEquals(expected, actual);
    }

    @Test
    void operate_ProductCountLessThenZero_notOk() {
        Product productCountLessThenZero = new Fruit(PRODUCT_NAME, -1);

        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> operation.operate(productCountLessThenZero));

        String actual = exception.getMessage();
        String expected = AMOUNT_CANNOT_BE_NEGATIVE + PRODUCT_NAME;
        assertEquals(expected, actual);
    }
}

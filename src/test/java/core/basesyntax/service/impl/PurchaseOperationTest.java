package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Product;
import core.basesyntax.service.RecordDataManipulation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String PRODUCT_NAME = "test";
    private static final int AMOUNT = 12;
    private static final String COUNT_CANNOT_BE_NEGATIVE_MSG =
            "Count cannot be negative. Product: ";
    private static final String NOT_ENOUGH_PRODUCTS_IN_STOCK_MSG =
            "Not enough products in stock. ProductName=";
    private final Product product = new Fruit(PRODUCT_NAME, AMOUNT);
    private final Storage storage = Storage.getInstance();
    private final RecordDataManipulation operation = new PurchaseOperation();

    @AfterEach
    void tearDown() {
        storage.getStorage().clear();
    }

    @Test
    void operate_FilledStorageIsUpdatedWithSameProduct_Ok() {
        storage.getStorage().put(PRODUCT_NAME, product);
        operation.operate(product);
        Product expected = new Fruit(PRODUCT_NAME, 0);
        Product actual = storage.getStorage().get(PRODUCT_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void operate_EmptyStorageIsUpdatedWithNewProduct_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> operation.operate(product));
        String expected = NOT_ENOUGH_PRODUCTS_IN_STOCK_MSG + PRODUCT_NAME;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void operate_StorageWithZeroCountProductIsUpdatedWithSameProductType_notOk() {
        Product zeroCountProduct = new Fruit(PRODUCT_NAME, 0);
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> operation.operate(zeroCountProduct));
        String expected = NOT_ENOUGH_PRODUCTS_IN_STOCK_MSG + PRODUCT_NAME;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void operate_ProductCountLessThenZero_notOk() {
        Product productCountLessThenZero = new Fruit(PRODUCT_NAME, -1);
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> operation.operate(productCountLessThenZero));
        String expected = COUNT_CANNOT_BE_NEGATIVE_MSG + PRODUCT_NAME;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }
}

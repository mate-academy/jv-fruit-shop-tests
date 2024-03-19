package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Product;
import core.basesyntax.service.RecordDataManipulation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String FIRST_PRODUCT_NAME = "test";
    private static final String SECOND_PRODUCT_NAME = "another_test";
    private static final int AMOUNT = 12;
    private static final String PRODUCT_NOT_FOUND_IN_DB_MSG =
            "Product not found in DB. ProductName=";
    private static final String COUNT_CANNOT_BE_NEGATIVE_MSG =
            "Count cannot be negative. Product: ";
    private final Product firstProduct = new Fruit(FIRST_PRODUCT_NAME, AMOUNT);
    private final Product secondProduct = new Fruit(SECOND_PRODUCT_NAME, AMOUNT);
    private final Storage storage = Storage.getInstance();
    private final RecordDataManipulation operation = new ReturnOperation();

    @AfterEach
    void tearDown() {
        storage.getStorage().clear();
    }

    @Test
    void operate_StorageWithExistingDataIsUpdatedWithSameProduct_ok() {
        storage.getStorage().put(FIRST_PRODUCT_NAME, firstProduct);
        operation.operate(firstProduct);
        Product expected = new Fruit(FIRST_PRODUCT_NAME, AMOUNT * 2);
        Product actual = storage.getStorage().get(FIRST_PRODUCT_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void operate_EmptyStorageIsUpdatedWithNewProduct_notOk() {
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> operation.operate(firstProduct));
        String expected = PRODUCT_NOT_FOUND_IN_DB_MSG + FIRST_PRODUCT_NAME;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void operate_FilledStorageIsUpdatedWithNewProduct_notOk() {
        storage.getStorage().put(FIRST_PRODUCT_NAME, firstProduct);
        Exception exception =
                assertThrows(RuntimeException.class,
                        () -> operation.operate(secondProduct));
        String expected = PRODUCT_NOT_FOUND_IN_DB_MSG + SECOND_PRODUCT_NAME;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void operate_ProductCountLessThenZero_notOk() {
        Product productCountLessThenZero = new Fruit(FIRST_PRODUCT_NAME, -1);
        Exception exception =
                assertThrows(IllegalArgumentException.class,
                        () -> operation.operate(productCountLessThenZero));
        String expected = COUNT_CANNOT_BE_NEGATIVE_MSG + FIRST_PRODUCT_NAME;
        String actual = exception.getMessage();
        assertEquals(expected, actual);
    }
}

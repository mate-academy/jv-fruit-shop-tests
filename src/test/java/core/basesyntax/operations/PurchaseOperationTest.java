package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.ProductDto;
import core.basesyntax.storage.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static final ProductDto VALID_PRODUCT = new ProductDto("p",
            "TEST_FRUIT",
            123);
    private static final ProductDto INVALID_PRODUCT = new ProductDto("p",
            "TEST_FRUIT",
            -123);
    private static final ProductDto NOT_EXIST_PRODUCT = new ProductDto("p",
            "NOT_EXIST_FRUIT",
            0);
    private static final Operation purchaseOperation = new PurchaseOperation();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Storage.fruits.put("TEST_FRUIT", 1234);
    }

    @Test
    public void validData_Ok() {
        purchaseOperation.apply(VALID_PRODUCT);
        int expected = 1111;
        int actual = Storage.fruits.get("TEST_FRUIT");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void negativeCapacityData_NotOk() {
        purchaseOperation.apply(INVALID_PRODUCT);
    }

    @Test(expected = RuntimeException.class)
    public void notExistData_NotOk() {
        purchaseOperation.apply(NOT_EXIST_PRODUCT);
    }
}

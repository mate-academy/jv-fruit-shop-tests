package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.ProductDto;
import core.basesyntax.storage.Storage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static final ProductDto validProduct = new ProductDto("p",
            "TEST_FRUIT",
            123);
    private static final ProductDto invalidProduct = new ProductDto("p",
            "TEST_FRUIT",
            -123);
    private static final ProductDto notExistsProduct = new ProductDto("p",
            "NOT_EXIST_FRUIT",
            0);
    private static final Operation purchaseOperation = new PurchaseOperation();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Storage.fruits.put("TEST_FRUIT", 1234);
    }

    @Test
    public void validData_Ok() {
        purchaseOperation.apply(validProduct);
        int expected = 1111;
        int actual = Storage.fruits.get("TEST_FRUIT");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void negativeCapacityData_NotOk() {
        purchaseOperation.apply(invalidProduct);
    }

    @Test(expected = RuntimeException.class)
    public void notExistData_NotOk() {
        purchaseOperation.apply(notExistsProduct);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }
}

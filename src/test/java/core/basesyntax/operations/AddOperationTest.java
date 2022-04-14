package core.basesyntax.operations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.ProductDto;
import core.basesyntax.storage.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationTest {
    private static final ProductDto validProduct = new ProductDto("r",
            "TEST_FRUIT",
            123);
    private static final Operation addOperation = new AddOperation();

    @BeforeClass
    public static void beforeClass() throws Exception {
        Storage.fruits.put("TEST_FRUIT", 0);
    }

    @Test
    public void validData_Ok() {
        addOperation.apply(validProduct);
        int expected = 123;
        int actual = Storage.fruits.get("TEST_FRUIT");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void invalidData_NotOk() {
        addOperation.apply(new ProductDto("r", "TEST_FRUIT", 0));
    }

    @Test(expected = RuntimeException.class)
    public void invalidDataNullProductName_NotOk() {
        addOperation.apply(new ProductDto("r", null, 0));
    }
}

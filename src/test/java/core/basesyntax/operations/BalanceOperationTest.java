package core.basesyntax.operations;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dto.ProductDto;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Test;

public class BalanceOperationTest {
    private static final ProductDto validProduct = new ProductDto("b",
            "TEST_FRUIT",
            123);
    private static final ProductDto invalidProduct = new ProductDto("b",
             null, 0);
    private static final Operation balanceOperation = new BalanceOperation();

    @Test
    public void validData_Ok() {
        balanceOperation.apply(validProduct);
        assertTrue(Storage.fruits.containsKey("TEST_FRUIT"));
    }

    @Test(expected = RuntimeException.class)
    public void invalidData_NotOK() {
        balanceOperation.apply(invalidProduct);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}

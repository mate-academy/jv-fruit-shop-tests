package core.basesyntax.strategy;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dto.ProductDto;
import core.basesyntax.storage.Storage;
import org.junit.Test;

public class OperationStrategyTest {
    private static final OperationStrategy operation = new OperationStrategy();

    @Test (expected = RuntimeException.class)
    public void invalidOperation_NotOk() {
        ProductDto productDto = new ProductDto("invalid", null, null);
        operation.doAction(productDto);
    }

    @Test
    public void validOperation_Ok() {
        ProductDto productDto = new ProductDto("b", "TEST_FRUIT", 123);
        operation.doAction(productDto);
        assertTrue(Storage.fruits.containsKey("TEST_FRUIT"));
    }

    @Test (expected = RuntimeException.class)
    public void nullOperation_NotOk() {
        ProductDto productDto = new ProductDto(null, null, null);
        operation.doAction(productDto);
    }
}

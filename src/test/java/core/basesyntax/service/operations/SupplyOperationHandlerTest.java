package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {

    private SupplyOperationHandler supplyOperationHandler;

    @BeforeEach
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
        Storage.fruits.clear();
    }

    @Test
    public void apply_validTransaction_Ok() {
        Storage.fruits.put(new Fruit("apple"), 0);
        FruitTransactionDto validDto = new FruitTransactionDto("s", "apple", 10);
        supplyOperationHandler.apply(validDto);
        assertEquals(10, Storage.fruits.get(new Fruit("apple")));
    }

    @Test
    public void isApplicable_validDto_Ok() {
        FruitTransactionDto validDto = new FruitTransactionDto("s", "apple", 10);
        assertTrue(supplyOperationHandler.isApplicable(validDto));
    }

    @Test
    public void isApplicable_invalidDto_NotOk() {
        FruitTransactionDto invalidDto = new FruitTransactionDto("b", "banana", 5);
        assertFalse(supplyOperationHandler.isApplicable(invalidDto));
    }
}

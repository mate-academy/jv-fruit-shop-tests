package core.basesyntax.service.operations;

import static core.basesyntax.service.operations.TetsObjects.INVALID_SUPPLY_DTO;
import static core.basesyntax.service.operations.TetsObjects.VALID_SUPPLY_DTO;
import static core.basesyntax.service.operations.TetsObjects.fruit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    public static final int INITIAL_QUANTITY = 0;
    public static final int EXPECTED_QUANTITY = 20;

    private SupplyOperationHandler supplyOperationHandler;

    @BeforeEach
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
        Storage.fruits.clear();
    }

    @Test
    public void apply_validTransaction_Ok() {
        Storage.fruits.put(fruit, INITIAL_QUANTITY);
        supplyOperationHandler.apply(VALID_SUPPLY_DTO);
        assertEquals(EXPECTED_QUANTITY, Storage.fruits.get(fruit));
    }

    @Test
    public void isApplicable_validDto_Ok() {
        assertTrue(supplyOperationHandler.isApplicable(VALID_SUPPLY_DTO));
    }

    @Test
    public void isApplicable_invalidDto_NotOk() {
        assertFalse(supplyOperationHandler.isApplicable(INVALID_SUPPLY_DTO));
    }
}

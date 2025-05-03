package core.basesyntax.service.operations;

import static core.basesyntax.service.operations.TetsObjects.INVALID_RETURN_DTO;
import static core.basesyntax.service.operations.TetsObjects.VALID_RETURN_DTO;
import static core.basesyntax.service.operations.TetsObjects.fruit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exeptions.UnsupportedOperationExeption;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    public static final int EXPECTED_NUMBER = 15;
    public static final int QUANTITY = 10;
    private ReturnOperationHandler returnOperationHandler;

    @BeforeEach
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        Storage.fruits.clear();
    }

    @Test
    public void apply_ValidDto_Ok() {
        Storage.fruits.put(fruit, QUANTITY);
        returnOperationHandler.apply(VALID_RETURN_DTO);

        assertEquals(EXPECTED_NUMBER, Storage.fruits.get(fruit));
    }

    @Test
    public void apply_InvalidOperationType_NotOk() {
        assertThrows(UnsupportedOperationExeption.class, ()
                -> returnOperationHandler.apply(INVALID_RETURN_DTO));
    }

    @Test
    public void isApplicable_ValidOperationType_Ok() {
        assertTrue(returnOperationHandler.isApplicable(VALID_RETURN_DTO));
    }
}

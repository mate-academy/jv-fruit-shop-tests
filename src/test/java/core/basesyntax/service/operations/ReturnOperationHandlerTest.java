package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exeptions.UnsupportedOperationExeption;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {

    private ReturnOperationHandler returnOperationHandler;

    @BeforeEach
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        Storage.fruits.clear();
    }

    @Test
    public void apply_ValidDto_Ok() {
        Storage.fruits.put(new Fruit("apple"), 10);
        FruitTransactionDto dto = new FruitTransactionDto("r", "apple", 5);
        returnOperationHandler.apply(dto);

        assertEquals(15, Storage.fruits.get(new Fruit("apple")));
    }

    @Test
    public void apply_InvalidOperationType_NotOk() {
        FruitTransactionDto dto = new FruitTransactionDto("x", "apple", 5);

        assertThrows(UnsupportedOperationExeption.class, () -> returnOperationHandler.apply(dto));
    }

    @Test
    public void isApplicable_ValidOperationType_Ok() {
        FruitTransactionDto dto = new FruitTransactionDto("r", "apple", 5);

        assertTrue(returnOperationHandler.isApplicable(dto));
    }
}

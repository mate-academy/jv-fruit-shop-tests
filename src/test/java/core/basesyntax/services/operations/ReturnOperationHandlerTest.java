package core.basesyntax.services.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dto.FruitTransactionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {

    private final OperationHandler handler = new ReturnOperationHandler();

    @BeforeEach
    void setUp() {
        fruitStorage.clear();
    }

    @Test
    void apply_AddsFruitToStorage_WhenNotExists_Ok() {
        FruitTransactionDto dto = new FruitTransactionDto("r", "Apple", 10);
        handler.apply(dto);
        assertEquals(10, fruitStorage.get("Apple"));
    }

    @Test
    void apply_IncreasesQuantity_WhenFruitExists_Ok() {
        fruitStorage.put("Apple", 5);
        FruitTransactionDto dto = new FruitTransactionDto("r", "Apple", 10);
        handler.apply(dto);
        assertEquals(15, fruitStorage.get("Apple"));
    }

    @Test
    void isApplicable_ReturnsTrue_ForSupplyOperationO_k() {
        FruitTransactionDto dto = new FruitTransactionDto("r", "Apple", 10);
        assertTrue(handler.isApplicable(dto));
    }

    @Test
    void isApplicable_ReturnsFalse_ForOtherOperations_Ok() {
        FruitTransactionDto dto = new FruitTransactionDto("p", "Apple", 10);
        assertFalse(handler.isApplicable(dto));
    }
}

package core.basesyntax.services.operations;

import static core.basesyntax.db.Storage.fruitStorage;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dto.FruitTransactionDto;
import core.basesyntax.exceptions.FruitStorageException;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {

    private final PurchaseOperationHandler handler = new PurchaseOperationHandler();

    @Test
    void apply_ThrowsException_WhenFruitNotInStorage_NotOk() {
        FruitTransactionDto dto = new FruitTransactionDto("p", "Apple", 10);

        assertThrows(FruitStorageException.class, () -> handler.apply(dto),
                "Expected FruitStorageException when fruit is not in storage");
    }

    @Test
    void apply_ThrowsException_WhenPurchaseAmountIsTooBig_NotOk() {
        FruitTransactionDto dto = new FruitTransactionDto("p", "Apple", 20);
        assertThrows(FruitStorageException.class, () -> handler.apply(dto),
                "Expected FruitStorageException when purchase amount is too big");
    }

    @Test
    void apply_ReducesQuantity_WhenPurchaseAmountIsValid_Ok() {
        FruitTransactionDto dto = new FruitTransactionDto("p", "Apple", 5);
        fruitStorage.put("Apple", 10);
        assertDoesNotThrow(() -> handler.apply(dto),
                "Expected no exception when purchase amount is valid");
        assertEquals(5, fruitStorage.get("Apple"), "Expected 5 apples left in storage");
    }
}

package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.SupplyOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new SupplyOperation();
    }

    @AfterEach
    void clearStorage() {
        FruitStorage.storage.clear();
    }

    @Test
    public void apply_validTransaction_addsFruitToStorage_Ok() {
        FruitStorage.storage.put("apple", 30);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 20);
        operationHandler.apply(transaction);
        assertEquals(Integer.valueOf(50), FruitStorage.storage.get("apple"));
    }

    @Test
    public void apply_NullOperation_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                operationHandler.apply(new FruitTransaction(null, "apple", 50))
        );
    }

    @Test
    public void apply_NullQuantity_CanNotAddQuantityToStorage_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                operationHandler.apply(new FruitTransaction(Operation.BALANCE, "apple", null))
        );
    }

    @Test
    public void apply_NullFruit_CanNotAddFruitToStorage_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                operationHandler.apply(new FruitTransaction(Operation.BALANCE, null, 50))
        );
    }

    @Test
    public void apply_NullTransaction_NotOk() {
        assertThrows(IllegalArgumentException.class, () ->
                operationHandler.apply(null)
        );
    }
}

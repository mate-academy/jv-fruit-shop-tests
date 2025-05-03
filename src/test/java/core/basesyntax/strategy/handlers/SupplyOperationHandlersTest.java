package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlersTest {
    private SupplyOperationHandlers supplyOperationHandlers;

    @BeforeEach
    void setUp() {
        supplyOperationHandlers = new SupplyOperationHandlers();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.storage.clear();
    }

    @Test
    void handle_fruitExists_supplyQuantity_Ok() {
        String fruit = "apple";
        Integer initialQuantity = 10;
        Integer quantityToSupply = 5;
        FruitStorage.storage.put(fruit, initialQuantity);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, fruit, quantityToSupply);
        supplyOperationHandlers.handle(transaction);
        assertEquals(initialQuantity + quantityToSupply, FruitStorage.storage.get(fruit));
    }

    @Test
    void handle_fruitExists_supplyNegativeQuantity_Ok() {
        String fruit = "apple";
        Integer initialQuantity = 10;
        Integer quantityToSupply = -3;
        FruitStorage.storage.put(fruit, initialQuantity);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, fruit, quantityToSupply);
        supplyOperationHandlers.handle(transaction);
        assertEquals(initialQuantity + quantityToSupply, FruitStorage.storage.get(fruit));
    }

    @Test
    void handle_fruitDoesNotExist_notOk() {
        String fruit = "orange";
        Integer quantityToSupply = 5;
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, fruit, quantityToSupply);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            supplyOperationHandlers.handle(transaction);
        });
        assertEquals("Database does not contain: " + fruit, exception.getMessage());
    }
}

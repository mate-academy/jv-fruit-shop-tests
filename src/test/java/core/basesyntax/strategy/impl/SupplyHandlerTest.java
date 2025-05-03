package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static SupplyHandler supplyHandler;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new SupplyHandler();
    }

    @DisplayName("Checking for handling null as fruitTransaction value")
    @Test
    void handle_nullFruitTransaction_notOk() {
        assertThrows(NullPointerException.class, () ->
                supplyHandler.handle(null));
    }

    @DisplayName("Checking for handling transaction with invalid amount to supply")
    @Test
    void handle_invalidAmountToReturn_notOk() {
        String fruit = "banana";
        int quantityToSupply = 0;
        FruitTransaction fruitTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN,
                        fruit,
                        quantityToSupply
                );
        assertThrows(RuntimeException.class, () ->
                supplyHandler.handle(fruitTransaction));
    }

    @DisplayName("Checking for handling normal supply transaction")
    @Test
    void handle_ok() {
        String fruit = "banana";
        int quantityToSupply = 10;
        FruitTransaction fruitTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN,
                        fruit,
                        quantityToSupply
                );
        int amountInStorage = 10;
        Storage.storageMap.put(fruit, amountInStorage);
        supplyHandler.handle(fruitTransaction);
        assertEquals(quantityToSupply + amountInStorage, (int) Storage.storageMap.get(fruit));
    }
}

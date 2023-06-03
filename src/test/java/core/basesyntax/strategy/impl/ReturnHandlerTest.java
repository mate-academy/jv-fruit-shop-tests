package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private ReturnHandler returnHandler;

    @BeforeEach
    void setUp() {
        returnHandler = new ReturnHandler();
    }

    @DisplayName("Checking for handling null as fruitTransaction value")
    @Test
    void handle_nullFruitTransaction_notOk() {
        assertThrows(NullPointerException.class, () ->
                returnHandler.handle(null));
    }

    @DisplayName("Checking for handling transaction with invalid amount to return")
    @Test
    void handle_invalidAmountToReturn_notOk() {
        String fruit = "banana";
        int quantityToReturn = 0;
        FruitTransaction fruitTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN,
                        fruit,
                        quantityToReturn
                );
        assertThrows(RuntimeException.class, () ->
                returnHandler.handle(fruitTransaction));
    }

    @DisplayName("Checking for handling normal return transaction")
    @Test
    void handle_ok() {
        String fruit = "banana";
        int quantityToReturn = 10;
        FruitTransaction fruitTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.RETURN,
                        fruit,
                        quantityToReturn
                );
        int amountInStorage = 10;
        Storage.storageMap.put(fruit, amountInStorage);
        returnHandler.handle(fruitTransaction);
        assertTrue(quantityToReturn + amountInStorage == Storage.storageMap.get(fruit));
    }
}

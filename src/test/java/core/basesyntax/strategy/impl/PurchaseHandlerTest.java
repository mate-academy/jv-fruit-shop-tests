package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandler();
    }

    @DisplayName("Checking for handling null as fruitTransaction value")
    @Test
    void handle_nullFruitTransaction_notOk() {
        assertThrows(NullPointerException.class, () ->
                purchaseHandler.handle(null));
    }

    @DisplayName("Checking for handling fruit transaction with "
            + "amount to buy bigger what amount in storage")
    @Test
    void handle_amountToBuyBiggerThanAmountInStorage_notOk() {
        String fruit = "banana";
        int quantityToBuy = 10;
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                fruit,
                quantityToBuy
        );
        Storage.storageMap.put(fruit, quantityToBuy - 1);
        assertThrows(RuntimeException.class, () ->
                purchaseHandler.handle(fruitTransaction));
    }

    @DisplayName("Checking for handling transaction")
    @Test
    void handle_ok() {
        String fruit = "banana";
        int quantityToBuy = 10;
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                fruit,
                quantityToBuy
        );
        Storage.storageMap.put(fruit, quantityToBuy + 1);
        purchaseHandler.handle(fruitTransaction);
        assertEquals(1, (int) Storage.storageMap.get(fruit));
    }
}

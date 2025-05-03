package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class TradeOperationHandlerTest {

    @Test
    public void handle_ValidTransaction_DeductsFromStorage_Ok() {
        TradeOperationHandler tradeHandler = new TradeOperationHandler();
        FruitStorage.FRUITS.put("Apple", 15);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "Apple", 5);

        tradeHandler.handle(transaction);

        assertEquals(10, FruitStorage.FRUITS.get("Apple"));
    }

    @Test
    public void handle_ValidTransaction_DeductsFromExistingQuantity_Ok() {
        TradeOperationHandler tradeHandler = new TradeOperationHandler();
        FruitStorage.FRUITS.put("Orange", 12);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "Orange", 8);

        tradeHandler.handle(transaction);

        assertEquals(4, FruitStorage.FRUITS.get("Orange"));
    }

    @Test
    public void handle_InsufficientQuantity_ThrowsRuntimeException_NotOk() {
        TradeOperationHandler tradeHandler = new TradeOperationHandler();
        FruitStorage.FRUITS.put("Grapes", 5);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "Grapes", 8);

        assertThrows(RuntimeException.class, () -> tradeHandler.handle(transaction));
    }

    @Test
    public void handle_NonExistingFruit_ThrowsRuntimeException_NotOk() {
        TradeOperationHandler tradeHandler = new TradeOperationHandler();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "NonExistingFruit", 5);

        assertThrows(RuntimeException.class, () -> tradeHandler.handle(transaction));
    }
}

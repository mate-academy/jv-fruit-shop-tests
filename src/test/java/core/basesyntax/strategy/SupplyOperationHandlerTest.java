package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final int INITIAL_QUANTITY = 20;
    private static final int SUPPLY_QUANTITY = 10;
    private static final int EMPTY_VALUE = 0;

    private OperationHandler supplyOperationHandler;

    @BeforeEach
    void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
        Storage.storage.clear();
    }

    @Test
    void handle_validTransaction_ok() {
        Storage.storage.put(FRUIT_NAME, INITIAL_QUANTITY);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        FRUIT_NAME,
                        SUPPLY_QUANTITY);
        supplyOperationHandler.handle(fruitTransaction);
        int expectedQuantity = INITIAL_QUANTITY + SUPPLY_QUANTITY;
        assertEquals(expectedQuantity, Storage.storage.get(FRUIT_NAME));
    }

    @Test
    void handle_invalidTransaction_notOk() {
        FruitTransaction fruitTransactionWithNullFruit =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, null, SUPPLY_QUANTITY);
        assertThrows(RuntimeException.class, () ->
                supplyOperationHandler.handle(fruitTransactionWithNullFruit));
        FruitTransaction fruitTransactionWithNullOperation =
                new FruitTransaction(null, FRUIT_NAME, SUPPLY_QUANTITY);
        assertThrows(RuntimeException.class, () ->
                supplyOperationHandler.handle(fruitTransactionWithNullOperation));
    }

    @Test
    void handle_noInitialQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                FRUIT_NAME,
                SUPPLY_QUANTITY);
        supplyOperationHandler.handle(fruitTransaction);
        assertEquals(SUPPLY_QUANTITY, Storage.storage.getOrDefault(FRUIT_NAME, EMPTY_VALUE));
    }
}

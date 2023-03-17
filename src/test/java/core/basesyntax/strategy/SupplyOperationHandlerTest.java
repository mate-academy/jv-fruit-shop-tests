package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final int INITIAL_QUANTITY = 20;
    private static final int SUPPLY_QUANTITY = 10;
    private static final int EMPTY_VALUE = 0;

    private OperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
        Storage.storage.clear();
    }

    @Test
    public void handle_validTransaction_ok() {
        Storage.storage.put(FRUIT_NAME, INITIAL_QUANTITY);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        FRUIT_NAME,
                        SUPPLY_QUANTITY);
        supplyOperationHandler.handle(fruitTransaction);
        int expectedQuantity = INITIAL_QUANTITY + SUPPLY_QUANTITY;
        assertEquals(expectedQuantity, (int) Storage.storage.get(FRUIT_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransactionWithNullFruit_notOk() {
        FruitTransaction fruitTransactionWithNullFruit =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, null, SUPPLY_QUANTITY);
        supplyOperationHandler.handle(fruitTransactionWithNullFruit);
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidTransactionWithNullOperation_notOk() {
        FruitTransaction fruitTransactionWithNullOperation =
                new FruitTransaction(null, FRUIT_NAME, SUPPLY_QUANTITY);
        supplyOperationHandler.handle(fruitTransactionWithNullOperation);
    }

    @Test
    public void handle_noInitialQuantity_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        FRUIT_NAME,
                        SUPPLY_QUANTITY);
        supplyOperationHandler.handle(fruitTransaction);
        assertEquals(SUPPLY_QUANTITY, (int) Storage.storage.getOrDefault(FRUIT_NAME, EMPTY_VALUE));
    }
}

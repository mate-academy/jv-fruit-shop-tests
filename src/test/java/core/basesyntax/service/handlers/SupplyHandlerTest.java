package core.basesyntax.service.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final FruitTransaction.Operation VALID_OPERATION
            = FruitTransaction.Operation.SUPPLY;
    private static final String VALID_FRUIT = "apple";
    private static final int VALID_QUANTITY = 20;
    private static final int SUPPLY_QUANTITY = 73;
    private static OperationHandler supplyHandler;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new SupplyHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void handle_ValidQuantity_ok() {
        Storage.storage.put(VALID_FRUIT, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, SUPPLY_QUANTITY);
        supplyHandler.handle(transaction);
        int expected = VALID_QUANTITY + SUPPLY_QUANTITY;
        assertEquals(expected, Storage.storage.get(transaction.getFruit()));
    }

    @Test
    void handle_EmptyStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, SUPPLY_QUANTITY);
        supplyHandler.handle(transaction);
        assertEquals(SUPPLY_QUANTITY, Storage.storage.get(VALID_FRUIT));
    }
}

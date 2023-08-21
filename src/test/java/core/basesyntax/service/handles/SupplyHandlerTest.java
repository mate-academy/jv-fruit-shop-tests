package core.basesyntax.service.handles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static OperationHandler supplyHandler;
    private static FruitTransaction.Operation VALID_OPERATION = FruitTransaction.Operation.SUPPLY;
    private static String VALID_FRUIT = "apple";
    private static int VALID_QUANTITY = 40;
    private static int VALID_SUPPLY_QUANTITY = 30;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new SupplyHandler();
    }

    @AfterEach
    static void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void supplyHandler_validQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, VALID_SUPPLY_QUANTITY);
        supplyHandler.handler(transaction);
        int expected = VALID_QUANTITY + VALID_SUPPLY_QUANTITY;
        assertEquals(expected, Storage.storage.get(transaction.getFruit()));
    }

    @Test
    void supplyHandler_emptyStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, VALID_SUPPLY_QUANTITY);
        supplyHandler.handler(transaction);
        assertEquals(VALID_SUPPLY_QUANTITY, Storage.storage.get(VALID_FRUIT));
    }
}

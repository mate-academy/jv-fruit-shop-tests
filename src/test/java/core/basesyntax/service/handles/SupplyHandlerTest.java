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
    private static int VALID_QUANTITY = 45;
    private static int VALID_SUPPLY_QUANTITY = 60;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new SupplyHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void supplyHandler_validQuantity_ok() {
        Storage.storage.put(VALID_FRUIT, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, VALID_SUPPLY_QUANTITY);
        supplyHandler.handler(transaction);
        int expected = VALID_QUANTITY + VALID_SUPPLY_QUANTITY;
        assertEquals(expected, Storage.storage.get(transaction.getFruit()));
    }

}

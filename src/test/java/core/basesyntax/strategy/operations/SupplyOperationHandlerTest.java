package core.basesyntax.strategy.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final Operation VALID_OPERATION = Operation.SUPPLY;
    private static final String VALID_FRUIT = "apple";
    private static final int VALID_QUANTITY = 20;
    private static final int SUPPLY_QUANTITY = 10;
    private static OperationHandler supplyHandler;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new SupplyOperationHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.getStorage().put(VALID_FRUIT, VALID_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void handleValidQuantity_Ok() {
        FruitTransaction transaction =
                new FruitTransaction(VALID_OPERATION, VALID_FRUIT, SUPPLY_QUANTITY);
        supplyHandler.handle(transaction);
        int expected = VALID_QUANTITY + SUPPLY_QUANTITY;
        assertEquals(expected, Storage.getStorage().get(transaction.getFruit()));
    }

    @Test
    void handleForEmptyStorage_Ok() {
        Storage.getStorage().clear();
        FruitTransaction transaction =
                new FruitTransaction(VALID_OPERATION, VALID_FRUIT, SUPPLY_QUANTITY);
        supplyHandler.handle(transaction);
        assertEquals(SUPPLY_QUANTITY, Storage.getStorage().get(transaction.getFruit()));
    }
}

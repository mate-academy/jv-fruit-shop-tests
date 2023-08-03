package core.basesyntax.strategy.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final Operation VALID_OPERATION = Operation.RETURN;
    private static final String VALID_FRUIT = "apple";
    private static final int VALID_QUANTITY = 20;
    private static final int RETURN_QUANTITY = 10;
    private static OperationHandler returnHandler;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new ReturnOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void handleValidQuantity_Ok() {
        Storage.getStorage().put(VALID_FRUIT, VALID_QUANTITY);
        FruitTransaction transaction =
                new FruitTransaction(VALID_OPERATION, VALID_FRUIT, RETURN_QUANTITY);
        returnHandler.handle(transaction);
        int expected = VALID_QUANTITY + RETURN_QUANTITY;
        assertEquals(expected, Storage.getStorage().get(transaction.getFruit()));
    }

    @Test
    void handleForEmptyStorage_Ok() {
        Storage.getStorage().clear();
        FruitTransaction transaction =
                new FruitTransaction(VALID_OPERATION, VALID_FRUIT, RETURN_QUANTITY);
        returnHandler.handle(transaction);
        assertEquals(RETURN_QUANTITY, Storage.getStorage().get(transaction.getFruit()));
    }
}

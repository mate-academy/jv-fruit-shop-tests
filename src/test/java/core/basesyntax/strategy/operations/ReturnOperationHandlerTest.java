package core.basesyntax.strategy.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterAll;
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
        Storage.getStorage().put(VALID_FRUIT, VALID_QUANTITY);
    }

    @AfterAll
    static void afterAll() {
        Storage.getStorage().clear();
    }

    @Test
    void checkHandleValidQuantity_Ok() {
        FruitTransaction transaction =
                new FruitTransaction(VALID_OPERATION, VALID_FRUIT, RETURN_QUANTITY);
        returnHandler.handle(transaction);
        int expected = VALID_QUANTITY + RETURN_QUANTITY;
        assertEquals(expected, Storage.getStorage().get(transaction.getFruit()));
    }
}

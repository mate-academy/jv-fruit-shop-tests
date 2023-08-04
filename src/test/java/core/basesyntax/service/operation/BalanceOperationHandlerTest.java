package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String DEFAULT_FRUIT_NAME = "apple";
    private static final Integer DEFAULT_FRUIT_QUANTITY = 20;
    private static final Operation BALANCE_OPERATION = Operation.BALANCE;
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    void execute_validFruitTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(DEFAULT_FRUIT_NAME,
                                                                DEFAULT_FRUIT_QUANTITY,
                                                                BALANCE_OPERATION);
        operationHandler.execute(fruitTransaction);
        int expected = DEFAULT_FRUIT_QUANTITY;
        int actual = Storage.storage.get(DEFAULT_FRUIT_NAME);
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}

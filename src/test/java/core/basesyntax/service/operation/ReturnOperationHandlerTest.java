package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final String DEFAULT_FRUIT_NAME = "apple";
    private static final Integer INITIAL_FRUIT_QUANTITY = 20;
    private static final Integer RETURNED_FRUIT_QUANTITY = 10;
    private static final Operation BALANCE_OPERATION = Operation.BALANCE;
    private static final Operation RETURN_OPERATION = Operation.RETURN;
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
        operationHandler = new ReturnOperationHandler();
        FruitTransaction fruitTransaction = new FruitTransaction(DEFAULT_FRUIT_NAME,
                INITIAL_FRUIT_QUANTITY,
                BALANCE_OPERATION);
        OperationHandler balanceOperation = new BalanceOperationHandler();
        balanceOperation.execute(fruitTransaction);
    }

    @Test
    void execute_returnedFruitTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(DEFAULT_FRUIT_NAME,
                RETURNED_FRUIT_QUANTITY,
                RETURN_OPERATION);
        operationHandler.execute(fruitTransaction);
        int expected = INITIAL_FRUIT_QUANTITY + RETURNED_FRUIT_QUANTITY;
        int actual = Storage.storage.get(DEFAULT_FRUIT_NAME);
        assertEquals(expected, actual);
    }
}

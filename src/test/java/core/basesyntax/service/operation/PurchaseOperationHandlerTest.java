package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidOperationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String DEFAULT_FRUIT_NAME = "apple";
    private static final Integer INITIAL_FRUIT_QUANTITY = 20;
    private static final Integer VALID_REQUIRED_FRUIT_QUANTITY = 10;
    private static final Integer OVERWHELMING_REQ_FRUIT_QUANTITY = INITIAL_FRUIT_QUANTITY + 1;
    private static final Operation PURCHASE_OPERATION = Operation.PURCHASE;
    private static final String ERROR_MESSAGE = "The required quantity "
                                                + OVERWHELMING_REQ_FRUIT_QUANTITY
                                                + " can't be sold, since the current quantity "
                                                + INITIAL_FRUIT_QUANTITY;

    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
        operationHandler = new PurchaseOperationHandler();
        FruitTransaction fruitTransaction = new FruitTransaction(DEFAULT_FRUIT_NAME,
                                                                INITIAL_FRUIT_QUANTITY,
                                                                Operation.BALANCE);
        OperationHandler balanceOperation = new BalanceOperationHandler();
        balanceOperation.execute(fruitTransaction);
    }

    @Test
    void execute_validFruitTransaction_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(DEFAULT_FRUIT_NAME,
                                                                VALID_REQUIRED_FRUIT_QUANTITY,
                                                                PURCHASE_OPERATION);
        operationHandler.execute(fruitTransaction);
        int expected = INITIAL_FRUIT_QUANTITY - VALID_REQUIRED_FRUIT_QUANTITY;
        int actual = Storage.storage.get(DEFAULT_FRUIT_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void execute_fruitTransactionWithOverwhelmingQuantity_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(DEFAULT_FRUIT_NAME,
                OVERWHELMING_REQ_FRUIT_QUANTITY,
                PURCHASE_OPERATION);
        InvalidOperationException actualException = assertThrows(InvalidOperationException.class,
                () -> operationHandler.execute(fruitTransaction));
        assertEquals(ERROR_MESSAGE, actualException.getMessage());
    }
}

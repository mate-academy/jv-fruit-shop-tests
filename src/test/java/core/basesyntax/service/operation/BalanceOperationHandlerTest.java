package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String FRUIT = "APPLE";
    private static final int VALID_QUANTITY = 10;
    private static final int ZERO_QUANTITY = 0;
    private static OperationHandler operationHandler;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
    }

    @BeforeEach
    void setUp() {
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void handleTransaction_handleValidTransaction_Ok() {
        transaction.setFruit(FRUIT);
        transaction.setQuantity(VALID_QUANTITY);
        operationHandler.handleTransaction(transaction);
        Integer expectedQuantity = VALID_QUANTITY;
        Integer actualQuantity = Storage.fruits.get(FRUIT);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void handleTransaction_handleTransactionQuantityZero_Ok() {
        transaction.setFruit(FRUIT);
        transaction.setQuantity(ZERO_QUANTITY);
        operationHandler.handleTransaction(transaction);
        Integer expectedQuantity = ZERO_QUANTITY;
        Integer actualQuantity = Storage.fruits.get(FRUIT);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void handleTransaction_handleTransactionNullFruit_Not_Ok() {
        transaction.setFruit(null);
        transaction.setQuantity(VALID_QUANTITY);
        assertThrows(RuntimeException.class, () ->
                operationHandler.handleTransaction(transaction));
    }

    @Test
    void handleTransaction_handleNullTransaction_Not_Ok() {
        transaction = null;
        assertThrows(RuntimeException.class, () ->
                operationHandler.handleTransaction(transaction));
    }
}

package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String FRUIT = "apple";
    private static final int CURRENT_STORAGE_QUANTITY = 20;
    private static final int VALID_QUANTITY = 10;
    private static OperationHandler operationHandler;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyOperationHandler();
    }

    @BeforeEach
    void setUp() {
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        Storage.fruits.put(FRUIT, CURRENT_STORAGE_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void handleTransaction_transactionWithValidQuantity_Ok() {
        transaction.setFruit(FRUIT);
        transaction.setQuantity(VALID_QUANTITY);
        operationHandler.handleTransaction(transaction);
        Integer expectedQuantity = CURRENT_STORAGE_QUANTITY + VALID_QUANTITY;
        Integer actualQuantity = Storage.fruits.get(FRUIT);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void handleTransaction_transactionWithNullFruit_NotOk() {
        transaction.setFruit(null);
        transaction.setQuantity(VALID_QUANTITY);
        assertThrows(RuntimeException.class, () ->
                operationHandler.handleTransaction(transaction));
    }

    @Test
    void handleTransaction_NullTransaction_NotOk() {
        transaction = null;
        assertThrows(RuntimeException.class, () ->
                operationHandler.handleTransaction(transaction));
    }
}

package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String FRUIT = "apple";
    private static final int BIGGER_THAN_CURRENT_QUANTITY = 900;
    private static final int LESS_THAN_CURRENT_QUANTITY = 5;
    private static final int CURRENT_STORAGE_QUANTITY = 20;
    private static OperationHandler operationHandler;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperationHandler();
    }

    @BeforeEach
    void setUp() {
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        Storage.fruits.put(FRUIT, CURRENT_STORAGE_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void handleTransaction_transactionWithQuantityLessThanCurrentValue_Ok() {
        transaction.setFruit(FRUIT);
        transaction.setQuantity(LESS_THAN_CURRENT_QUANTITY);
        operationHandler.handleTransaction(transaction);
        Integer expectedQuantity = CURRENT_STORAGE_QUANTITY - LESS_THAN_CURRENT_QUANTITY;
        Integer actualQuantity = Storage.fruits.get(FRUIT);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void handleTransaction_transactionWithQuantityBiggerThanCurrentValue_NotOk() {
        transaction.setFruit(FRUIT);
        transaction.setQuantity(BIGGER_THAN_CURRENT_QUANTITY);
        assertThrows(RuntimeException.class, () ->
                operationHandler.handleTransaction(transaction));
    }

    @Test
    void handleTransaction_transactionNullFruit_NotOk() {
        transaction.setFruit(null);
        transaction.setQuantity(LESS_THAN_CURRENT_QUANTITY);
        assertThrows(RuntimeException.class, () ->
                operationHandler.handleTransaction(transaction));
    }

    @Test
    void handleTransaction_nullTransaction_NotOk() {
        transaction = null;
        assertThrows(RuntimeException.class, () ->
                operationHandler.handleTransaction(transaction));
    }
}

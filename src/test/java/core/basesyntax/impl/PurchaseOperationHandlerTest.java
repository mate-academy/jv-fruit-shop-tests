package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static Map<String, Integer> fruit;
    private static OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperationHandler();
    }

    @BeforeEach
    public void setUp() {
        fruit = Storage.FRUIT_MAPS;
        fruit.put("apple", 35);
    }

    @AfterAll
    static void afterAll() {
        fruit.clear();
    }

    @Test
    void purchase_validOperation_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 15);
        operationHandler.operate(fruitTransaction);
        int actual = fruit.get("apple");
        int expected = 35;
        assertEquals(expected, actual);
    }

    @Test
    void purchase_zeroValue_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 0);
        operationHandler.operate(fruitTransaction);
        int actual = fruit.get("apple");
        int expected = 35;
        assertEquals(expected, actual);
    }

    @Test
    void purchase_null_notOk() {
        assertThrows(RuntimeException.class, () -> operationHandler.operate(null));
    }

    @Test
    public void purchase_invalid_operation_notOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 36);
        assertThrows(RuntimeException.class, () -> operationHandler.operate(fruitTransaction));
    }
}

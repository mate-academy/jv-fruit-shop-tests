package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ReturnOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private static Map<String, Integer> fruit;
    private static OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new ReturnOperationHandler();
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
    void return_null_notOk() {
        assertThrows(RuntimeException.class, () -> operationHandler.operate(null));
    }

    @Test
    public void return_ValidOperation_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 15);
        operationHandler.operate(fruitTransaction);
        int actual = fruit.get("apple");
        int expected = 50;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void return_ValidOperation_zeroValue_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 0);
        operationHandler.operate(fruitTransaction);
        int actual = fruit.get("apple");
        int expected = 35;
        Assertions.assertEquals(expected, actual);
    }
}

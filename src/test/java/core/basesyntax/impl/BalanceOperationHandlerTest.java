package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static Map<String, Integer> fruit;
    private static OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceOperationHandler();
    }

    @BeforeEach
    public void setUp() {
        fruit = Storage.FRUIT_MAPS;
    }

    @AfterAll
    static void afterAll() {
        fruit.clear();
    }

    @Test
    void balance_null_notOk() {
        assertThrows(RuntimeException.class, () -> operationHandler.operate(null));
    }

    @Test
    public void balanceOperation_valid_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20);
        operationHandler.operate(fruitTransaction);
        int actual = fruit.get("apple");
        int expected = 20;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void balanceOperation_zeroValid_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 0);
        operationHandler.operate(fruitTransaction);
        int actual = fruit.get("apple");
        int expected = 0;
        Assertions.assertEquals(expected, actual);
    }
}

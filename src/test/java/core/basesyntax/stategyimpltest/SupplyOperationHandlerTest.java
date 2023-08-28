package core.basesyntax.stategyimpltest;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.SupplyOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static Map<String, Integer> fruit;
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        operationHandler = new SupplyOperationHandler();
        fruit = Storage.fruit;
        fruit.put("apple", 35);
    }

    @AfterAll
    static void afterAll() {
        fruit.clear();
    }

    @Test
     public void supplyOperation_validOperation_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 15);
        operationHandler.operate(fruitTransaction);
        int actual = fruit.get("apple");
        int expected = 50;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void supplyOperation_zeroValue_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 0);
        operationHandler.operate(fruitTransaction);
        int actual = fruit.get("apple");
        int expected = 35;
        Assertions.assertEquals(expected, actual);
    }
}

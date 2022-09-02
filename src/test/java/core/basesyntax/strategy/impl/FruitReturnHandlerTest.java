package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitReturnHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new FruitReturnHandler(new FruitDaoImpl());
        fruit = new Fruit("banana");
    }

    @Test
    public void proceed_isValid() {
        Storage.fruits.put(fruit, 10);
        operationHandler.proceed(
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        fruit,
                        5)
        );
        assertEquals("Expected value : 15",
                Integer.valueOf(15),
                Storage.fruits.get(fruit));
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}

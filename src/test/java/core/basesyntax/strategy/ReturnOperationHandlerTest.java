package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        operationHandler = new ReturnOperationHandler();
    }

    @Test
    public void handleReturnOperation_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("apple"), 10);
        operationHandler.process(fruitTransaction);
        int actual = Storage.fruitStorage.get(fruitTransaction.getFruit());
        int expected = fruitTransaction.getQuantity();
        assertEquals(expected, actual);
    }

    @After
    public void cleanUp() {
        Storage.fruitStorage.clear();
    }
}

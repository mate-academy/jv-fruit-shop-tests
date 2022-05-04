package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void handleBalanceOperation_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                new Fruit("banana"), 100);
        operationHandler.process(fruitTransaction);
        int expected = Storage.fruitStorage.get(fruitTransaction.getFruit());
        int actual = fruitTransaction.getQuantity();
        assertEquals(expected, actual);
    }

    @After
    public void cleanUp() {
        Storage.fruitStorage.clear();
    }
}

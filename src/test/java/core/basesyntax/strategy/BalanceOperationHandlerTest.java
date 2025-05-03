package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler balanceHandler;
    private Fruit fruit;

    @Before
    public void setUp() {
        balanceHandler = new BalanceOperationHandler();
        fruit = new Fruit("kiwi");
    }

    @Test
    public void handle_BalanceCheck_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction("b", fruit, 22);
        balanceHandler.handle(fruitTransaction);
        Integer expected = 22;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }
}

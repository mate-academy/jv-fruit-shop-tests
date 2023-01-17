package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.DataBase;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerBalanceTest {
    private final HandlerConditionFactory handlerCondition = new HandlerConditionFactory();
    private Fruit lemon;

    @Before
    public void setUp() {
        lemon = new Fruit();
        lemon.setBalance(50);
        DataBase.fruitsInShop.put("lemon", lemon);
    }

    @Test
    public void handle_ok() {
        handlerCondition.getHandler("b").handle("lemon", "50");
        int expected = 100;
        assertEquals(expected, lemon.getBalance());
    }

    @After
    public void tearDown() {
        DataBase.fruitsInShop.clear();
    }
}

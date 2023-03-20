package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BalanceHandlerTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 50;
    private static OperationHandler handler;
    private static FruitTransaction fruitTransaction;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        handler = new BalanceHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    FRUIT, QUANTITY);

    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void apply_alreadyExistentFruit_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Table already have a " + FRUIT + " balance");
        handler.apply(fruitTransaction);
        handler.apply(fruitTransaction);
    }

    @Test
    public void apply_regularAddition_Ok() {
        handler.apply(fruitTransaction);
        Map<String, Integer> expected = Map.of(FRUIT, QUANTITY);
        assertEquals(expected, FruitStorage.fruitStorage);
    }
}

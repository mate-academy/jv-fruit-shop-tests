package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BalanceHandlerTest {
    private static final int BALANCE_QUANTITY = 40;
    private static final Integer EXPECTED_QUANTITY = 40;
    private static final String FRUIT = "apple";
    private static FruitTransaction fruitTransaction;
    private static OperationHandler handler;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, FRUIT, BALANCE_QUANTITY);
        handler = new BalanceHandler();
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void apply_RegularCalculatingCase_Ok() {
        handler.apply(fruitTransaction);
        Map<String, Integer> expected = Map.of(FRUIT, EXPECTED_QUANTITY);
        assertEquals(expected, FruitStorage.fruitStorage);
    }

    @Test
    public void apply_NullAsArgumentInput_NotOk() {
        expectedException.expect(NullPointerException.class);
        handler.apply(null);
    }
}
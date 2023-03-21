package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseHandlerTest {
    private static final int INITIAL_STORAGE_QUANTITY = 50;
    private static final int PURCHASE_QUANTITY = 40;
    private static final Integer EXPECTED_QUANTITY = 10;
    private static final int TOO_MUCH_FRUITS_QUANTITY = 90;
    private static final String FRUIT = "apple";
    private static FruitTransaction fruitTransaction;
    private static OperationHandler handler;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                FRUIT, PURCHASE_QUANTITY);
        handler = new PurchaseHandler();
    }

    @Before
    public void setUp() throws Exception {
        FruitStorage.fruitStorage.put(FRUIT, INITIAL_STORAGE_QUANTITY);
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
    public void apply_NotEnoughFruitsToPurchase_NotOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                FRUIT, TOO_MUCH_FRUITS_QUANTITY);
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Not enough "
                + fruitTransaction.getFruit() + "'s on store to purchase");
        handler.apply(fruitTransaction);
    }

    @Test
    public void apply_NullAsArgumentInput_NotOk() {
        expectedException.expect(NullPointerException.class);
        handler.apply(null);
    }
}

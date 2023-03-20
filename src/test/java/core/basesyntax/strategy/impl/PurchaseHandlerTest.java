package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseHandlerTest {
    private static final String FRUIT = "apple";
    private static final int INITIAL_STORAGE_QUANTITY = 90;
    private static final int PURCHASE_QUANTITY = 50;
    private static final Integer EXPECTED_QUANTITY = 40;
    private static final int LOW_INITIAL_QUANTITY = 30;
    private static OperationHandler handler;
    private static FruitTransaction fruitTransaction;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        handler = new PurchaseHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    FRUIT, PURCHASE_QUANTITY);
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void apply_regularAddition_Ok() {
        FruitStorage.fruitStorage.put(FRUIT, INITIAL_STORAGE_QUANTITY);
        handler.apply(fruitTransaction);
        assertEquals(EXPECTED_QUANTITY, FruitStorage.fruitStorage.get(FRUIT));
    }

    @Test
    public void apply_notEnoughFruitsToPurchase_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Not enough "
                + FRUIT + "'s on store to purchase");
        FruitStorage.fruitStorage.put(FRUIT, LOW_INITIAL_QUANTITY);
        handler.apply(fruitTransaction);
    }
}

package core.basesyntax.strategy.actions.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.actions.ActionWithFruits;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActionWithFruitsPurchaseTest {
    private static final int INVALID_AMOUNT = -13;
    private static final int VALID_AMOUNT = 23;
    private static final int MAX_NUMBER = Integer.MAX_VALUE;
    private static final String VALID_NAME = "apple";
    private static ActionWithFruits actionWithFruits;

    @BeforeClass
    public static void beforeClass() {
        actionWithFruits = new ActionWithFruitsPurchase();
    }

    @Test(expected = RuntimeException.class)
    public void getAmountAfterAction_with_null_name_notOk() {
        actionWithFruits.getAmountAfterAction(null, VALID_AMOUNT);
        fail("Should throw an exception with null name");
    }

    @Test(expected = RuntimeException.class)
    public void getAmountAfterAction_with_empty_name_notOk() {
        actionWithFruits.getAmountAfterAction("", VALID_AMOUNT);
        fail("Should throw an exception with empty name");
    }

    @Test(expected = RuntimeException.class)
    public void getAmountAfterAction_with_invalid_amount_notOk() {
        actionWithFruits.getAmountAfterAction(VALID_NAME, INVALID_AMOUNT);
        fail("Should throw an exception amount where amount less than 0");
    }

    @Test(expected = RuntimeException.class)
    public void getAmountAfterAction_when_not_enough_quantity_in_storage_notOk() {
        actionWithFruits.getAmountAfterAction(VALID_NAME, MAX_NUMBER);
        fail("Should throw an exception if input amount more than amount in storage");
    }

    @Test
    public void getAmountAfterAction_Ok() {
        Storage.put(VALID_NAME, VALID_AMOUNT);
        int expectedAmount = Storage.get(VALID_NAME) - VALID_AMOUNT;
        actionWithFruits.getAmountAfterAction(VALID_NAME, VALID_AMOUNT);
        assertEquals(expectedAmount, (int) Storage.get(VALID_NAME));
    }
}

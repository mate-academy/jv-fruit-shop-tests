package core.basesyntax.strategy.actions.impl;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.actions.ActionWithFruits;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActionWithFruitsSupplyTest {
    private static final int INVALID_AMOUNT = -13;
    private static final int VALID_AMOUNT = 23;
    private static final String VALID_NAME = "apple";
    private static ActionWithFruits actionWithFruits;

    @BeforeClass
    public static void beforeClass() {
        actionWithFruits = new ActionWithFruitsSupply();
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

    @Test
    public void getAmountAfterAction_Ok() {
        Storage.put(VALID_NAME, VALID_AMOUNT);
        int expectedAmount = VALID_AMOUNT + VALID_AMOUNT;
        actionWithFruits.getAmountAfterAction(VALID_NAME, VALID_AMOUNT);
        assertSame(expectedAmount, Storage.get(VALID_NAME));
    }
}

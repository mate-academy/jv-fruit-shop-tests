package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ActionStrategy;
import core.basesyntax.service.actiontype.ActionStrategyBalance;
import core.basesyntax.service.actiontype.ActionStrategyProducer;
import core.basesyntax.service.actiontype.ActionStrategyReturner;
import core.basesyntax.service.actiontype.ActionStrategySupplier;
import core.basesyntax.service.actiontype.ActionType;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActionStrategyImplTest {
    private static final Map<String, ActionType> mapStrategy = new HashMap<>();
    private static final int BALANCE_MULTIPLICATOR = 0;
    private static final int PRODUCER_MULTIPLICATOR = 1;
    private static final int SUPPLIER_MULTIPLICATOR = -1;
    private static final int RETURNER_MULTIPLICATOR = -1;
    private static final int TEST_AMOUNT = 10;
    private static ActionStrategy actionStrategy;

    @BeforeClass
    public static void beforeClass() throws Exception {
        mapStrategy.put("b", new ActionStrategyBalance());
        mapStrategy.put("p", new ActionStrategyProducer());
        mapStrategy.put("s", new ActionStrategySupplier());
        mapStrategy.put("r", new ActionStrategyReturner());
        actionStrategy = new ActionStrategyImpl(mapStrategy);
    }

    @Test
    public void testValidStrategyBalance_Ok() {
        int testValue = TEST_AMOUNT;
        int expected = actionStrategy.get("b").getNewValue(testValue);
        assertEquals(expected, testValue * BALANCE_MULTIPLICATOR);
    }

    @Test
    public void testValidStrategyProducer_Ok() {
        int testValue = TEST_AMOUNT;
        int expected = actionStrategy.get("p").getNewValue(testValue);
        assertEquals(expected, testValue * PRODUCER_MULTIPLICATOR);
    }

    @Test
    public void testValidStrategySupplier_Ok() {
        int testValue = TEST_AMOUNT;
        int expected = actionStrategy.get("s").getNewValue(testValue);
        assertEquals(expected, testValue * SUPPLIER_MULTIPLICATOR);
    }

    @Test
    public void testValidStrategyReturner_Ok() {
        int testValue = TEST_AMOUNT;
        int expected = actionStrategy.get("r").getNewValue(testValue);
        assertEquals(expected, testValue * RETURNER_MULTIPLICATOR);
    }

    @After
    public void tearDown() throws Exception {
        Storage.data.clear();
    }
}

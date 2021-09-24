package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    private static final String[] normalRecord = new String[]{"b", "cherry", "100"};
    private static final String[] normalRecord1 = new String[]{"b", "cherry", "50"};
    private static final String[] incorrectRecord1 = new String[]{"HelloWorld!", "cherry", "50"};
    private static final ActivityHandler balanceHandler = new BalanceHandler();
    private static String expected;

    @Before
    public void setUp() throws Exception {
        Storage.fruitsQuantity.clear();
    }

    @Test
    public void balanceHandler_addNewBalance_Ok() {
        FruitRecord fruitRecord = new FruitRecord(normalRecord);
        balanceHandler.apply(fruitRecord);
        assertTrue(Storage.fruitsQuantity.containsKey(fruitRecord.getFruit())
                && Storage.fruitsQuantity.containsValue(fruitRecord.getAmount()));
    }

    @Test
    public void balanceHandler_updateBalance_Ok() {
        FruitRecord fruitRecord = new FruitRecord(normalRecord);
        FruitRecord fruitRecord1 = new FruitRecord(normalRecord1);
        balanceHandler.apply(fruitRecord);
        balanceHandler.apply(fruitRecord1);
        assertEquals(fruitRecord1.getAmount(),
                (int) Storage.fruitsQuantity.get(fruitRecord.getFruit()));
    }

    @Test(expected = RuntimeException.class)
    public void balanceHandler_incorrectData_notOk() {
        FruitRecord fruitRecord = new FruitRecord(incorrectRecord1);
        balanceHandler.apply(fruitRecord);
        assertEquals(expected, Storage.fruitsQuantity.get(fruitRecord.getFruit()));
    }
}

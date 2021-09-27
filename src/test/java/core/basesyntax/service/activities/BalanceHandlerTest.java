package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    private static final String[] NORMAL_RECORD = new String[]{"b", "cherry", "100"};
    private static final String[] SECOND_NORMAL_RECORD = new String[]{"b", "cherry", "50"};
    private static final String[] INCORRECT_RECORD = new String[]{"HelloWorld!", "cherry", "50"};
    private static final ActivityHandler BALANCE_HANDLER = new BalanceHandler();
    private static String expected;

    @Before
    public void setUp() {
        Storage.FRUITS_QUANTITY.clear();
    }

    @Test
    public void balanceHandler_addNewBalance_Ok() {
        FruitRecord fruitRecord = new FruitRecord(NORMAL_RECORD);
        BALANCE_HANDLER.apply(fruitRecord);
        assertTrue("Can't write this record to db " + Arrays.toString(NORMAL_RECORD),
                Storage.FRUITS_QUANTITY.containsKey(fruitRecord.getFruit())
                && Storage.FRUITS_QUANTITY.get(fruitRecord.getFruit())
                        .equals(fruitRecord.getAmount())
                && Storage.FRUITS_QUANTITY.size() == 1);
    }

    @Test
    public void balanceHandler_updateBalance_Ok() {
        FruitRecord fruitRecord = new FruitRecord(NORMAL_RECORD);
        FruitRecord fruitRecord1 = new FruitRecord(SECOND_NORMAL_RECORD);
        BALANCE_HANDLER.apply(fruitRecord);
        BALANCE_HANDLER.apply(fruitRecord1);
        assertEquals("Can't update data in db " + Arrays.toString(SECOND_NORMAL_RECORD),
                fruitRecord1.getAmount(),
                (int) Storage.FRUITS_QUANTITY.get(fruitRecord.getFruit()));
    }

    @Test(expected = RuntimeException.class)
    public void balanceHandler_incorrectData_notOk() {
        FruitRecord fruitRecord = new FruitRecord(INCORRECT_RECORD);
        BALANCE_HANDLER.apply(fruitRecord);
    }
}

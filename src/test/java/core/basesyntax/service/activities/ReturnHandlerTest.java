package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;
import org.junit.Before;
import org.junit.Test;

public class ReturnHandlerTest {
    private static final FruitRecord RECORD_ORANGE_BALANCE
            = new FruitRecord(new String[]{"b", "orange", "100"});
    private static final FruitRecord RECORD_ORANGE_RETURN
            = new FruitRecord(new String[]{"r", "orange", "8"});
    private static final FruitRecord RECORD_ORANGE_BIG_RETURN
            = new FruitRecord(new String[]{"r", "orange", "10000000"});
    private static final FruitRecord RECORD_CHERRY_RETURN
            = new FruitRecord(new String[]{"r", "cherry", "100"});
    private static final ActivityHandler RETURN_HANDLER = new ReturnHandler();
    private static final ActivityHandler BALANCE_HANDLER = new BalanceHandler();
    private String expected;

    @Before
    public void setUp() {
        Storage.FRUITS_QUANTITY.clear();
    }

    @Test
    public void returnHandler_normalData_Ok() {
        BALANCE_HANDLER.apply(RECORD_ORANGE_BALANCE);
        RETURN_HANDLER.apply(RECORD_ORANGE_RETURN);
        assertEquals("Can't update data in db " + RECORD_ORANGE_RETURN,
                108, (int) Storage.FRUITS_QUANTITY.get(RECORD_ORANGE_RETURN.getFruit()));
    }

    @Test(expected = RuntimeException.class)
    public void returnHandler_impossibleReturn_notOk() {
        BALANCE_HANDLER.apply(RECORD_ORANGE_BALANCE);
        RETURN_HANDLER.apply(RECORD_CHERRY_RETURN);
    }

    @Test
    public void returnHandler_veryBigReturn_Ok() {
        BALANCE_HANDLER.apply(RECORD_ORANGE_BALANCE);
        RETURN_HANDLER.apply(RECORD_ORANGE_BIG_RETURN);
        assertEquals("Can't update data in db " + RECORD_ORANGE_BIG_RETURN,
                10000100, (int) Storage.FRUITS_QUANTITY.get(RECORD_ORANGE_BIG_RETURN.getFruit()));
    }
}

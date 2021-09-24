package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;
import org.junit.Before;
import org.junit.Test;

public class ReturnHandlerTest {
    private static final FruitRecord recordOrangeBalance
            = new FruitRecord(new String[]{"b", "orange", "100"});
    private static final FruitRecord recordOrangeReturn
            = new FruitRecord(new String[]{"r", "orange", "8"});
    private static final FruitRecord recordOrangeBigReturn
            = new FruitRecord(new String[]{"r", "orange", "10000000"});
    private static final FruitRecord recordCherryReturn
            = new FruitRecord(new String[]{"r", "cherry", "100"});
    private static final ActivityHandler returnHandler = new ReturnHandler();
    private static final ActivityHandler balanceHandler = new BalanceHandler();
    private String expected;

    @Before
    public void setUp() {
        Storage.fruitsQuantity.clear();
    }

    @Test
    public void returnHandler_normalData_Ok() {
        balanceHandler.apply(recordOrangeBalance);
        returnHandler.apply(recordOrangeReturn);
        assertEquals(108, (int) Storage.fruitsQuantity.get(recordOrangeReturn.getFruit()));
    }

    @Test(expected = RuntimeException.class)
    public void returnHandler_impossibleReturn_notOk() {
        balanceHandler.apply(recordOrangeBalance);
        returnHandler.apply(recordCherryReturn);
    }

    @Test
    public void returnHandler_veryBigReturn_Ok() {
        balanceHandler.apply(recordOrangeBalance);
        returnHandler.apply(recordOrangeBigReturn);
        assertEquals(10000100, (int) Storage.fruitsQuantity.get(recordOrangeBigReturn.getFruit()));
    }
}

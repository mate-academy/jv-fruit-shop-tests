package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {

    private static final ActivityHandler BALANCE_HANDLER = new BalanceHandler();
    private static final ActivityHandler SUPPLY_HANDLER = new SupplyHandler();
    private static final FruitRecord RECORD_ORANGE_BALANCE
            = new FruitRecord(new String[]{"b", "orange", "100"});
    private static final FruitRecord RECORD_ORANGE_SUPPLY
            = new FruitRecord(new String[]{"s", "orange", "50"});
    private static String expected;

    @Before
    public void setUp() {
        Storage.FRUITS_QUANTITY.clear();
    }

    @Test
    public void supplyHandler_addNewSupplyInEmptyDb_Ok() {
        SUPPLY_HANDLER.apply(RECORD_ORANGE_SUPPLY);
        assertTrue("Can't write this record to db " + RECORD_ORANGE_SUPPLY,
                Storage.FRUITS_QUANTITY.containsKey(RECORD_ORANGE_SUPPLY.getFruit())
                && Storage.FRUITS_QUANTITY.containsValue(RECORD_ORANGE_SUPPLY.getAmount()));
    }

    @Test
    public void supplyHandler_normalSupply_Ok() {
        BALANCE_HANDLER.apply(RECORD_ORANGE_BALANCE);
        SUPPLY_HANDLER.apply(RECORD_ORANGE_SUPPLY);
        assertEquals("Can't write this record to db " + RECORD_ORANGE_SUPPLY,
                150, (int) Storage.FRUITS_QUANTITY.get(RECORD_ORANGE_SUPPLY.getFruit()));
    }
}

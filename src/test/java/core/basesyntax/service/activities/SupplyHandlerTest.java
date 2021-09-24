package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {

    private static final ActivityHandler balanceHandler = new BalanceHandler();
    private static final ActivityHandler supplyHandler = new SupplyHandler();
    private static final FruitRecord recordOrangeBalance
            = new FruitRecord(new String[]{"b", "orange", "100"});
    private static final FruitRecord recordOrangeSupply
            = new FruitRecord(new String[]{"s", "orange", "50"});
    private static String expected;

    @Before
    public void setUp() {
        Storage.fruitsQuantity.clear();
    }

    @Test
    public void supplyHandler_addNewSupplyInEmptyDb_Ok() {
        supplyHandler.apply(recordOrangeSupply);
        assertTrue(Storage.fruitsQuantity.containsKey(recordOrangeSupply.getFruit())
                && Storage.fruitsQuantity.containsValue(recordOrangeSupply.getAmount()));
    }

    @Test
    public void supplyHandler_normalSupply_Ok() {
        balanceHandler.apply(recordOrangeBalance);
        supplyHandler.apply(recordOrangeSupply);
        assertEquals(150, (int) Storage.fruitsQuantity.get(recordOrangeSupply.getFruit()));
    }
}

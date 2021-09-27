package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private ActivityHandler purchaseHandler;
    private ActivityHandler balanceHandler;
    private FruitRecord recordOrangeBalance;
    private FruitRecord recordCherryBalance;
    private FruitRecord recordOrangePurchase;
    private FruitRecord recordSecondOrangePurchase;
    private int expected;

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseHandler();
        balanceHandler = new BalanceHandler();
        recordOrangeBalance = new FruitRecord(new String[]{"b", "orange", "100"});
        recordCherryBalance = new FruitRecord(new String[]{"b", "cherry", "200"});
        recordOrangePurchase = new FruitRecord(new String[]{"p", "orange", "50"});
        recordSecondOrangePurchase = new FruitRecord(new String[]{"p", "orange", "100"});
        Storage.FRUITS_QUANTITY.clear();
    }

    @Test
    public void purchaseHandler_correctData_Ok() {
        balanceHandler.apply(recordOrangeBalance);
        purchaseHandler.apply(recordOrangePurchase);
        int expected = 50;
        assertEquals("Can't write this record to db " + recordOrangePurchase,
                expected, (int)Storage.FRUITS_QUANTITY.get(recordOrangePurchase.getFruit()));
    }

    @Test
    public void purchaseHandler_toZeroBalance_Ok() {
        balanceHandler.apply(recordOrangeBalance);
        purchaseHandler.apply(recordSecondOrangePurchase);
        assertEquals("Can't write this record to db " + recordSecondOrangePurchase,
                0,
                (int) Storage.FRUITS_QUANTITY.get(recordSecondOrangePurchase.getFruit()));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_missingFruitInStorage_NotOk() {
        balanceHandler.apply(recordCherryBalance);
        purchaseHandler.apply(recordOrangePurchase);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_tooMuchPurchase_NotOk() {
        balanceHandler.apply(recordOrangeBalance);
        purchaseHandler.apply(recordOrangePurchase);
        purchaseHandler.apply(recordSecondOrangePurchase);
    }
}

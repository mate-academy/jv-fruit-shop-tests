package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.fruitrecord.FruitRecord;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final String[] balanceOrange = new String[]{"b", "orange", "100"};
    private static final String[] balanceCherry = new String[]{"b", "cherry", "200"};
    private static final String[] purchaseOrange = new String[]{"p", "orange", "50"};
    private static final String[] purchaseOrange1 = new String[]{"p", "orange", "100"};
    private static final ActivityHandler purchaseHandler = new PurchaseHandler();
    private static final ActivityHandler balanceHandler = new BalanceHandler();
    private static final FruitRecord recordOrangeBalance = new FruitRecord(balanceOrange);
    private static final FruitRecord recordCherryBalance = new FruitRecord(balanceCherry);
    private static final FruitRecord recordOrangePurchase = new FruitRecord(purchaseOrange);
    private static final FruitRecord recordOrangePurchase1 = new FruitRecord(purchaseOrange1);
    private static int expected;

    @Before
    public void setUp() {
        Storage.fruitsQuantity.clear();
    }

    @Test
    public void purchaseHandler_correctData_Ok() {
        balanceHandler.apply(recordOrangeBalance);
        purchaseHandler.apply(recordOrangePurchase);
        int expected = 50;
        assertEquals(expected, (int)Storage.fruitsQuantity.get(recordOrangePurchase.getFruit()));
    }

    @Test
    public void purchaseHandler_toZeroBalance_Ok() {
        balanceHandler.apply(recordOrangeBalance);
        purchaseHandler.apply(recordOrangePurchase1);
        assertEquals(0,
                (int) Storage.fruitsQuantity.get(recordOrangePurchase1.getFruit()));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_missingFruitInStorage_NotOk() {
        balanceHandler.apply(recordCherryBalance);
        purchaseHandler.apply(recordOrangePurchase);
        assertEquals(expected,
                Storage.fruitsQuantity.get(recordOrangePurchase.getFruit()).getClass());
    }

    @Test(expected = RuntimeException.class)
    public void purchaseHandler_tooMuchPurchase_NotOk() {
        balanceHandler.apply(recordOrangeBalance);
        purchaseHandler.apply(recordOrangePurchase);
        purchaseHandler.apply(recordOrangePurchase1);
        assertEquals(expected,
                Storage.fruitsQuantity.get(recordOrangePurchase.getFruit()).getClass());
    }
}

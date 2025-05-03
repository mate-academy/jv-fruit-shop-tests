package core.basesyntax.models;

import core.basesyntax.models.activities.BalanceActivityHandler;
import core.basesyntax.models.activities.PurchaseActivityHandler;
import core.basesyntax.models.activities.ReturnActivityHandler;
import core.basesyntax.models.activities.SupplyActivityHandler;
import org.junit.Assert;
import org.junit.Test;

public class TestActivityHandlers {

    @Test
    public void testHandler_Ok() {
        Assert.assertEquals(10, new BalanceActivityHandler().apply(0, 10).intValue());
        Assert.assertEquals(15, new SupplyActivityHandler().apply(0, 15).intValue());
        Assert.assertEquals(20, new ReturnActivityHandler().apply(0, 20).intValue());
        Assert.assertEquals(10, new PurchaseActivityHandler().apply(20, 10).intValue());
    }

    @Test
    public void testHandler_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> new PurchaseActivityHandler().apply(10, 100));
    }

    @Test
    public void testTypeOfActivity_FromCode_Ok() {
        Assert.assertEquals(FruitTransaction.TypeOfActivity.BALANCE,
                FruitTransaction.TypeOfActivity.fromCode("b"));
        Assert.assertEquals(FruitTransaction.TypeOfActivity.SUPPLY,
                FruitTransaction.TypeOfActivity.fromCode("s"));
    }

    @Test
    public void testTypeOfActivityFromCode_InvalidCode_notOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> FruitTransaction.TypeOfActivity.fromCode("x"));
    }

}

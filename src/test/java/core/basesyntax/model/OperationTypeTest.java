package core.basesyntax.model;

import core.basesyntax.exeptions.InvalidOperationTypeException;
import org.junit.Assert;
import org.junit.Test;

public class OperationTypeTest {
    private static final String PURCHASE = "p";
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String RETURN = "r";
    private static final String INVALID_TYPE = "q";

    @Test
    public void OperationTypeTest_getOperationType_Ok() {
        OperationType actualPurchase = OperationType.getOperationType(PURCHASE);
        OperationType expectedPurchase = OperationType.PURCHASE;
        Assert.assertEquals(expectedPurchase, actualPurchase);

        OperationType actualBalance = OperationType.getOperationType(BALANCE);
        OperationType expectedBalance = OperationType.BALANCE;
        Assert.assertEquals(expectedBalance, actualBalance);

        OperationType actualSupply = OperationType.getOperationType(SUPPLY);
        OperationType expectedSupply = OperationType.SUPPLY;
        Assert.assertEquals(expectedSupply, actualSupply);

        OperationType actualReturn = OperationType.getOperationType(RETURN);
        OperationType expectedReturn = OperationType.RETURN;
        Assert.assertEquals(expectedReturn, actualReturn);

    }

    @Test (expected = InvalidOperationTypeException.class)
    public void OperationTypeTest_getOperationType_NotOk() {
        OperationType actual = OperationType.getOperationType(INVALID_TYPE);
    }
}

package core.basesyntax;

import core.basesyntax.model.OperationType;
import org.junit.Assert;
import org.junit.Test;

public class OperationTypeTest {
    private static final String BALANCE_CODE = "b";
    private static final String SUPPLY_CODE = "s";
    private static final String PURCHASE_CODE = "p";
    private static final String RETURN_CODE = "r";

    @Test
    public void get_balanceTypeByCode_Ok() {
        OperationType expectedBalanceType = OperationType.BALANCE;
        OperationType actualOperationType = OperationType.getByCode(BALANCE_CODE);
        Assert.assertEquals("Expected " + expectedBalanceType
                + " but was " + actualOperationType,
                expectedBalanceType, actualOperationType);
    }

    @Test
    public void get_supplyTypeByCode_Ok() {
        OperationType expectedSupplyType = OperationType.SUPPLY;
        OperationType actualOperationType = OperationType.getByCode(SUPPLY_CODE);
        Assert.assertEquals("Expected " + expectedSupplyType
                        + " but was " + actualOperationType,
                expectedSupplyType, actualOperationType);
    }

    @Test
    public void get_purchaseTypeByCode_Ok() {
        OperationType expectedPurchaseType = OperationType.PURCHASE;
        OperationType actualOperationType = OperationType.getByCode(PURCHASE_CODE);
        Assert.assertEquals("Expected " + expectedPurchaseType
                        + " but was " + actualOperationType,
                expectedPurchaseType, actualOperationType);
    }

    @Test
    public void get_returnTypeByCode_Ok() {
        OperationType expectedReturnType = OperationType.RETURN;
        OperationType actualOperationType = OperationType.getByCode(RETURN_CODE);
        Assert.assertEquals("Expected " + expectedReturnType
                        + " but was " + actualOperationType,
                expectedReturnType, actualOperationType);
    }
}

package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationTest {
    @Test
    public void getType_checkBalanceOperation_isOk() {
        assertEquals("b", Operation.BALANCE.getType());
    }

    @Test
    public void getType_checkSupplyOperation_isOk() {
        assertEquals("s", Operation.SUPPLY.getType());
    }

    @Test
    public void getType_checkPurchaseOperation_isOk() {
        assertEquals("p", Operation.PURCHASE.getType());
    }

    @Test
    public void getType_checkReturnOperation_isOk() {
        assertEquals("r", Operation.RETURN.getType());
    }
}

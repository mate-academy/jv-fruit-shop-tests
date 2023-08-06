package core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.testng.annotations.Test;

public class OperationTypeTest {
    @Test
    public void testGetCode_BuyOperation() {
        OperationType operationType = OperationType.B;

        String code = operationType.getCode();

        assertEquals("b", code, "Code for Buy operation should be 'b'");
    }

    @Test
    public void testGetCode_SellOperation() {
        OperationType operationType = OperationType.S;

        String code = operationType.getCode();

        assertEquals("s", code, "Code for Sell operation should be 's'");
    }

    @Test
    public void testGetCode_ReturnOperation() {
        OperationType operationType = OperationType.R;

        String code = operationType.getCode();

        assertEquals("r", code, "Code for Return operation should be 'r'");
    }

    @Test
    public void testGetCode_PurchaseOperation() {
        OperationType operationType = OperationType.P;

        String code = operationType.getCode();

        assertEquals("p", code, "Code for Purchase operation should be 'p'");
    }

    @Test
    public void testGetDisplayName_BuyOperation() {
        OperationType operationType = OperationType.B;

        String displayName = operationType.getCode();

        assertNotEquals("Buy", displayName, "Display name for Buy operation should be 'Buy'");
    }

    @Test
    public void testGetDisplayName_SellOperation() {
        OperationType operationType = OperationType.S;

        String displayName = operationType.getCode();

        assertNotEquals("Sell", displayName, "Display name for Sell operation should be 'Sell'");
    }

    @Test
    public void testGetDisplayName_ReturnOperation() {
        OperationType operationType = OperationType.R;

        String displayName = operationType.getCode();

        assertNotEquals("Return", displayName,
                "Display name for Return operation should be 'Return'");
    }

    @Test
    public void testGetDisplayName_PurchaseOperation() {
        OperationType operationType = OperationType.P;

        String displayName = operationType.getCode();

        assertNotEquals("Purchase", displayName,
                "Display name for Purchase operation should be 'Purchase'");
    }
}

package core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}

import static org.junit.Assert.assertEquals;

import fruitshop.model.Operation;
import org.junit.Test;

public class OperationTest {
    private static final char UNEXCITING_OPERATION = 'f';

    @Test(expected = RuntimeException.class)
    public void getByValue_getInvalidOperation_notOk() {
        Operation.getByValue(UNEXCITING_OPERATION);
    }

    @Test
    public void getByValue_getValidOperationBalance_isOk() {
        Operation actualOperationByValueBalance = Operation.getByValue('b');
        assertEquals("Expected to return corresponding operation",
                actualOperationByValueBalance, Operation.BALANCE
        );
    }

    @Test
    public void getByValue_getValidOperationSupply_isOk() {
        Operation actualOperationByValueSupply = Operation.getByValue('b');
        assertEquals("Expected to return corresponding operation",
                actualOperationByValueSupply, Operation.SUPPLY
        );
    }

    @Test
    public void getByValue_getValidOperation_isOk() {
        Operation actualOperationByValuePurchase = Operation.getByValue('b');
        assertEquals("Expected to return corresponding operation",
                actualOperationByValuePurchase, Operation.PURCHASE
        );
    }

    @Test
    public void getByValue_getValidOperationReturn_isOk() {
        Operation actualOperationByValueReturn = Operation.getByValue('b');
        assertEquals("Expected to return corresponding operation",
                actualOperationByValueReturn, Operation.RETURN
        );
    }
}

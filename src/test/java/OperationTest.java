import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import fruitshop.model.Operation;
import org.junit.Test;

public class OperationTest {
    private static final char UNEXCITING_OPERATION = 'f';

    @Test(expected = RuntimeException.class)
    public void getByValue_getInvalidOperation_notOk() {
        Operation.getByValue(UNEXCITING_OPERATION);
    }

    @Test
    public void getByValue_getValidOperation_isOk() {
        try {
            Operation actualOperationByValueBalance = Operation.getByValue('b');
            assertEquals("Expected to return corresponding operation",
                    actualOperationByValueBalance, Operation.BALANCE
            );
            Operation actualOperationByValueSupply = Operation.getByValue('s');
            assertEquals("Expected to return corresponding operation",
                    actualOperationByValueSupply, Operation.SUPPLY
            );
            Operation actualOperationByValuePurchase = Operation.getByValue('p');
            assertEquals("Expected to return corresponding operation",
                    actualOperationByValuePurchase, Operation.PURCHASE
            );
            Operation actualOperationByValueReturn = Operation.getByValue('r');
            assertEquals("Expected to return corresponding operation",
                    actualOperationByValueReturn, Operation.RETURN
            );
        } catch (Exception e) {
            fail("For existing operations method shouldn't throw an error");
        }
    }
}

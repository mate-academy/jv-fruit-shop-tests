import static org.junit.Assert.fail;

import fruitshop.model.Operation;
import org.junit.Test;

public class TestOperation {
    private static final char UNEXCITING_OPERATION = 'f';

    @Test(expected = RuntimeException.class)
    public void getByValue_getInvalidOperation_notOk() {
        Operation.getByValue(UNEXCITING_OPERATION);
    }

    @Test
    public void getByValue_getValidOperation_isOk() {
        try {
            for (char character : new char[]{ 'b', 's', 'p', 'r' }) {
                Operation.getByValue(character);
            }
        } catch (Exception e) {
            fail("For existing operations method shouldn't throw an error");
        }
    }
}

package core.basesyntax.model;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitTransactionTest {

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void retrieveByOperation_nullOperation_NotOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Fruit operation cannot be null");
        Operation.retrieveByOperation(null);
    }

    @Test
    public void retrieveByOperation_invalidOperation_NotOk() {
        String operation = "w";
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No such fruit operation type: " + operation);
        Operation.retrieveByOperation(operation);
    }

    @Test
    public void retrieveByOperation_emptyOperation_NotOk() {
        String operation = "";
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No such fruit operation type: " + operation);
        Operation.retrieveByOperation(operation);
    }

    @Test
    public void retrieveByOperation_validOperation_Ok() {
        Operation actualResult1 = Operation.retrieveByOperation("b");
        assertEquals(Operation.BALANCE, actualResult1);
        Operation actualResult2 = Operation.retrieveByOperation("r");
        assertEquals(Operation.RETURN, actualResult2);
        Operation actualResult3 = Operation.retrieveByOperation("s");
        assertEquals(Operation.SUPPLY, actualResult3);
        Operation actualResult4 = Operation.retrieveByOperation("p");
        assertEquals(Operation.PURCHASE, actualResult4);
    }
}

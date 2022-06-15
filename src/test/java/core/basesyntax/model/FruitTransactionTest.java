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
    public void getOperationByLetter_nullOperation_NotOk() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No such fruit operation type: " + null);
        Operation.getOperationByLetter(null);
    }

    @Test
    public void getOperationByLetter_invalidOperation_NotOk() {
        String operation = "w";
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No such fruit operation type: " + operation);
        Operation.getOperationByLetter(operation);
    }

    @Test
    public void getOperationByLetter_emptyOperation_NotOk() {
        String operation = "";
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No such fruit operation type: " + operation);
        Operation.getOperationByLetter(operation);
    }

    @Test
    public void getOperationByLetter_validOperation_Ok() {
        Operation actualResult1 = Operation.getOperationByLetter("b");
        assertEquals(Operation.BALANCE, actualResult1);
        Operation actualResult2 = Operation.getOperationByLetter("r");
        assertEquals(Operation.RETURN, actualResult2);
        Operation actualResult3 = Operation.getOperationByLetter("s");
        assertEquals(Operation.SUPPLY, actualResult3);
        Operation actualResult4 = Operation.getOperationByLetter("p");
        assertEquals(Operation.PURCHASE, actualResult4);
    }
}

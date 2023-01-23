package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationTest {

    @Test
    public void getOperationByFirstLetter_validOperationBLetter_ok() {
        Operation actual = Operation.getOperationByFirstLetter("b");
        Operation expected = Operation.BALANCE;
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByFirstLetter_validOperationSLetter_ok() {
        Operation actual = Operation.getOperationByFirstLetter("s");
        Operation expected = Operation.SUPPLY;
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByFirstLetter_validOperationRLetter_ok() {
        Operation actual = Operation.getOperationByFirstLetter("r");
        Operation expected = Operation.RETURN;
        assertEquals(expected, actual);
    }

    @Test
    public void getOperationByFirstLetter_validOperationPLetter_ok() {
        Operation actual = Operation.getOperationByFirstLetter("p");
        Operation expected = Operation.PURCHASE;
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getOperationByFirstLetter_inValidOperationLetter_notOk() {
        Operation.getOperationByFirstLetter("g");
    }

    @Test (expected = RuntimeException.class)
    public void getOperationByFirstLetter_inValidOperationNumber_notOk() {
        Operation.getOperationByFirstLetter("5");
    }

    @Test (expected = RuntimeException.class)
    public void getOperationByFirstLetter_inValidOperationLetterNull_notOk() {
        Operation.getOperationByFirstLetter(null);
    }
}

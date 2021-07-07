package core.basesyntax.service.impl;

import core.basesyntax.dto.Transaction;
import core.basesyntax.service.Parser;
import org.junit.Assert;
import org.junit.Test;

public class ParserImplTest {
    private static final String CORRECT_INPUT_DATA = "b,coconut,20";
    private static final String INCORRECT_INPUT_DATA = "@,7989465,dog";
    private Parser parser = new ParserImpl(new FruitValidator());

    @Test
    public void correctInputData_Ok() {
        Transaction expected = new Transaction("b", "coconut", 20);
        Transaction actual = parser.parseLine(CORRECT_INPUT_DATA);
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getOperation(), actual.getOperation());
        Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
    }

    @Test(expected = RuntimeException.class)
    public void incorrectInputData_NotOk() {
        parser.parseLine(INCORRECT_INPUT_DATA);
    }
}

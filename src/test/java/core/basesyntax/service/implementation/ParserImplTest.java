package core.basesyntax.service.implementation;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import core.basesyntax.strategy.OperationType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserImplTest {
    private Parser<TransactionDto> parser;

    @Before
    public void setUp() {
        parser = new ParserImpl(new ValidatorImpl());
    }

    @Test
    public void parseTo_validData_ok() {
        TransactionDto expected = new TransactionDto(OperationType.b, "banana", 28);
        TransactionDto actual = parser.parseTo("b,banana,28");
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void parseTo_invalidInputData_notOk() {
        parser.parseTo("a,88,");
    }

    @Test(expected = NullPointerException.class)
    public void parseTo_nullData_notOk() {
        parser.parseTo(null);
    }
}

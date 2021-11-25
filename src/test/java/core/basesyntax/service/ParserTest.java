package core.basesyntax.service;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.ValidatorImpl;
import core.basesyntax.strategy.OperationType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserTest {
    private Parser<TransactionDto> parser;

    @Before
    public void setUp() {
        parser = new ParserImpl(new ValidatorImpl());
    }

    @Test
    public void parseTo_validData_ok() {
        TransactionDto expected = new TransactionDto(OperationType.b, "banana", 25);
        TransactionDto actual = parser.parseTo("b,banana,25");
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void parseTo_invalidInputData_notOk() {
        parser.parseTo("a,56,");
    }

    @Test(expected = NullPointerException.class)
    public void parseTo_nullData_notOk() {
        parser.parseTo(null);
    }
}

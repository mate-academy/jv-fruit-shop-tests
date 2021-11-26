package core.basesyntax.service.implementation;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import core.basesyntax.strategy.OperationType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser<TransactionDto> parser;

    @BeforeClass
    public static void setUp() {
        parser = new ParserImpl(new ValidatorImpl());
    }

    @Test
    public void parseTo_validData_ok() {
        TransactionDto expected = new TransactionDto(OperationType.b, "banana", 28);
        TransactionDto actual = parser.parseTo("b,banana,28");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseTo_invalidData_notOk() {
        parser.parseTo("a,88,");
    }

    @Test(expected = NullPointerException.class)
    public void parseTo_nullData_notOk() {
        parser.parseTo(null);
    }
}

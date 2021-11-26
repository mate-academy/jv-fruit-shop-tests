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
    public void parse_validData_ok() {
        TransactionDto expected = new TransactionDto(OperationType.b, "banana", 28);
        TransactionDto actual = parser.parse("b,banana,28");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidData_notOk() {
        parser.parse("a,88,");
    }

    @Test(expected = NullPointerException.class)
    public void parse_nullData_notOk() {
        parser.parse(null);
    }
}

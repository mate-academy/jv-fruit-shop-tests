package core.basesyntax;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.Parser;
import core.basesyntax.service.impl.ParserImpl;
import core.basesyntax.service.impl.ValidatorImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static Parser parser;
    private static TransactionDto expected;
    private static String line;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl(new ValidatorImpl());
        line = "b,banana,10";
    }

    @Test
    public void correctParseFromLine_ok() {
        expected = new TransactionDto("b", "banana", 10);
        Assert.assertEquals(expected, parser.parseLine(line));
    }
}

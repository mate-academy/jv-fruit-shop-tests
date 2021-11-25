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
    private static TransactionDto transactionDto;
    private static String line;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl(new ValidatorImpl());
        transactionDto = new TransactionDto("b", "banana", 10);
        line = "b,banana,10";
    }

    @Test
    public void correctParseFromLine_ok() {
        Assert.assertEquals(transactionDto, parser.parseLine(line));
    }
}

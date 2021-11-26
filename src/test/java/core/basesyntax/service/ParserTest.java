package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.TransactionDto;
import core.basesyntax.service.parser.Parser;
import core.basesyntax.service.parser.ParserImpl;
import core.basesyntax.service.validator.Validator;
import core.basesyntax.service.validator.ValidatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static Validator validator;
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
        parser = new ParserImpl(validator);
    }

    @Test
    public void parseLine_correctWork_ok() {
        String line = "r,apple,20";
        TransactionDto actual = new TransactionDto("r","apple",20);
        TransactionDto expected = parser.parseLine(line);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void parseLine_null_notOk() {
        String line = null;
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_withOutOperation_notOk() {
        String line = "apple,100";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_withOutFruit_notOk() {
        String line = "p,,100";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_withOutQuantity_notOk() {
        String line = "p,apple,";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_emptyLine_notOk() {
        String line = "";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_incorrectLine_notOk() {
        String line = "o,apricot,-100";
        parser.parseLine(line);
    }
}

package core.basesyntax.servicetests;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.TransactionDto;
import core.basesyntax.service.parser.ParserImpl;
import core.basesyntax.service.validator.ValidatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static ValidatorImpl validator;
    private static ParserImpl parser;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
        parser = new ParserImpl(validator);
    }

    @Test
    public void parser_correctWork_ok() {
        String line = "r,apple,20";
        TransactionDto actual = new TransactionDto("r","apple",20);
        TransactionDto expected = parser.parseLine(line);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void parser_Null_notOk() {
        String line = null;
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parser_withOutOperation_notOk() {
        String line = "apple,100";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parser_withOutFruit_notOk() {
        String line = "p,,100";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parser_withOutQuantity_notOk() {
        String line = "p,apple,";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parser_emptyLine_notOk() {
        String line = "";
        parser.parseLine(line);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_incorrectLine_notOk() {
        String line = "o,apricot,-100";
        parser.parseLine(line);
    }
}

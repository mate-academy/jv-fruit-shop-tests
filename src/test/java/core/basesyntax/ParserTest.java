package core.basesyntax;

import core.basesyntax.service.Parser;
import core.basesyntax.service.ParserImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {

    private static final String INCORRECT_AMOUNT_LINE = "type,fruit,quantity";
    private static final String INCORRECT_LINE = "&Ufdj5##a,4fas23-12v%#v";
    private static final String EMPTY_LINE = "";
    private static final String ONE_ELEMENT_LINE = "type";
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_nullString_NotOk() {
        parser.parseLine(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_emptyLine_NotOk() {
        parser.parseLine(EMPTY_LINE);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_incorrectAmountLine_NotOk() {
        parser.parseLine(INCORRECT_AMOUNT_LINE);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_incorrectLine_NotOk() {
        parser.parseLine(INCORRECT_LINE);
    }

    @Test(expected = RuntimeException.class)
    public void parseLine_oneElementLine_NotOk() {
        parser.parseLine(ONE_ELEMENT_LINE);
    }
}

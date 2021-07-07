package core.basesyntax.service.impl;

import core.basesyntax.dto.Transaction;
import core.basesyntax.service.Parser;
import java.util.Set;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl(Set.of("b", "s", "r", "p"));
    }

    @Test
    public void parseUsualState_ok() {
        String line = "p,apple,15";
        Transaction expected = new Transaction("p", "apple", 15);
        Transaction actual = parser.parseLine(line);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parserEmptyLine_notOk() {
        parser.parseLine("");
    }

    @Test(expected = RuntimeException.class)
    public void parserInvalidFruitQuantity_notOk() {
        parser.parseLine("p,apple,-2");
    }

    @Test(expected = RuntimeException.class)
    public void parserIvalidOperation_notOk() {
        parser.parseLine("g, apple, 1");
    }
}

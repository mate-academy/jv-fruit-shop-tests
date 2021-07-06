package core.basesyntax;

import core.basesyntax.dto.Transaction;
import core.basesyntax.service.Parser;
import core.basesyntax.service.Validator;
import core.basesyntax.service.impl.FruitParser;
import core.basesyntax.service.impl.FruitValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserTest {
    private static Validator fruitValidator;
    private static Parser fruitParser;

    @BeforeClass
    public static void beforeClass() {
        fruitValidator = new FruitValidator();
        fruitParser = new FruitParser(fruitValidator);
    }

    @Test
    public void parser_parseLine_Ok() {
        String line = "b,apple,90";
        Transaction expected = new Transaction("b", "apple", 90);
        Assert.assertEquals(expected, fruitParser.parse(line));
    }

    @Test (expected = RuntimeException.class)
    public void parser_parseLineWithNegativeQuantity_notOk() {
        String line = "b,apple,-5";
        fruitParser.parse(line);
    }

    @Test (expected = RuntimeException.class)
    public void parser_parseLineWithCharQuantity_notOk() {
        String line = "b,apple,abc";
        fruitParser.parse(line);
    }

    @Test (expected = RuntimeException.class)
    public void parser_parseLineWithWrongOperation_notOk() {
        String line = "c,apple,4";
        fruitParser.parse(line);
    }
}

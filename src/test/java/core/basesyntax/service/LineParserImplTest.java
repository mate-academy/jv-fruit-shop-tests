package core.basesyntax.service;

import core.basesyntax.model.ParsedLine;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.LineParser;

public class LineParserImplTest {
    private static List<String> input;
    private static LineParser parser;
    private static List<ParsedLine> expected;
    private List<ParsedLine> actual;

    @BeforeClass
    public static void init() {
        input = new ArrayList<>();
        expected = new ArrayList<>();
        parser = new LineParserImpl(new InputDataValidatorImpl());
    }

    @Test
    public void parser_ok() {

        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");

        expected.add(new ParsedLine("b", "banana", 20));
        expected.add(new ParsedLine("b", "apple", 100));

        actual = parser.lineParcer(input);
        Assert.assertEquals(expected, actual);
    }
}

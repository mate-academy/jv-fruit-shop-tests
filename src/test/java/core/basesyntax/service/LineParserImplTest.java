package core.basesyntax.service;

import core.basesyntax.model.ParseLine;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import service.LineParser;

public class LineParserImplTest {
    private List<String> input;
    private LineParser parser;
    private List<ParseLine> expected;
    private List<ParseLine> actual;

    @Test
    public void parser_ok() {
        input = new ArrayList<>();
        input.add("type,fruit,quantity");
        input.add("b,banana,20");
        input.add("b,apple,100");
        expected = new ArrayList<>();
        expected.add(new ParseLine("b", "banana", 20));
        expected.add(new ParseLine("b", "apple", 100));
        parser = new LineParserImpl();
        actual = parser.lineParcer(input);
        Assert.assertEquals(expected, actual);
    }
}

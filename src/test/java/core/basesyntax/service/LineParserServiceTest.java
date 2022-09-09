package core.basesyntax.service;

import core.basesyntax.service.impl.LineParserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LineParserServiceTest {
    private LineParserService lineParser;
    private List<String> lines;

    @Before
    public void setUp() {
        lineParser = new LineParserServiceImpl();
        lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");
        lines.add("s,banana,100");
    }

    @Test
    public void parse_validDate_ok() {
        List<String[]> expected = new ArrayList<>();
        List<String[]> actual = lineParser.parseDate(lines);
        expected.add(new String[] {"b", "banana", "20"});
        expected.add(new String[] {"b", "apple", "100"});
        expected.add(new String[] {"s", "banana", "100"});
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i)[0],actual.get(i)[0]);
            Assert.assertEquals(expected.get(i)[1],actual.get(i)[1]);
            Assert.assertEquals(expected.get(i)[2],actual.get(i)[2]);
        }
    }

    @Test(expected = RuntimeException.class)
    public void parseDate_nullDate_notOk() {
        lineParser.parseDate(null);
    }
}

package core.basesyntax.service.serviceimpl;

import core.basesyntax.model.Record;
import core.basesyntax.service.RecordParser;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecordParserImplTest {
    private static final RecordParser parser = new RecordParserImpl();

    @Before
    public void setUp() {
    }

    @Test
    public void parse_defaultInput_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,apple,800",
                "b,banana,1000",
                "p,apple,99",
                "r,banana,36",
                "s,apple,800");
        List<Record> expected = List.of(
                new Record("b", "apple", 800),
                new Record("b", "banana", 1000),
                new Record("p", "apple", 99),
                new Record("r", "banana", 36),
                new Record("s", "apple", 800));
        List<Record> actual = parser.parseRecords(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parse_noTransactions_ok() {
        List<String> input = List.of(
                "type,fruit,quantity");
        List<Record> expected = Collections.emptyList();
        List<Record> actual = parser.parseRecords(input);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_nullInput_notOk() {
        List<String> input = null;
        parser.parseRecords(input);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidFormat_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,apple,800g",
                "b,banana,1000",
                "p,apple,99",
                "r,banana,36",
                "s,apple,800");
        parser.parseRecords(input);
    }

    @Test(expected = RuntimeException.class)
    public void parse_missingHeader_notOk() {
        List<String> input = List.of(
                "b,apple,800",
                "b,banana,1000",
                "p,apple,99",
                "r,banana,36",
                "s,apple,800");
        parser.parseRecords(input);
    }
}

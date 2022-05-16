package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserTest {
    private static FruitTransactionParser fruitTransactionParser;
    private List<String> lines;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Before
    public void setUp() {
        lines = new ArrayList<>();
    }

    @Test(expected = RuntimeException.class)
    public void parse_empty_notOk() {
        fruitTransactionParser.parse(lines);
    }

    @Test(expected = RuntimeException.class)
    public void parse_header_notOk() {
        lines.add("");
        lines.add("b,banana,20");
        fruitTransactionParser.parse(lines);
    }

    @Test(expected = RuntimeException.class)
    public void parse_null_notOk() {
        lines = null;
        fruitTransactionParser.parse(lines);
    }

    @Test(expected = RuntimeException.class)
    public void parse_invalidData_notOk() {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,20");
        fruitTransactionParser.parse(lines);
    }
}

package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.impl.ParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static Parser parser;
    private static List<String> rawData;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
        rawData = new ArrayList<>();
    }

    @Before
    public void setUp() {
        rawData.add("type,fruit,quantity");
    }

    @Test
    public void parseValidData_ok() {
        List<Transaction> expected = new ArrayList<>();
        expected.add(new Transaction(Transaction.Operation.SUPPLY, "lemon", 10));
        rawData.add("s,lemon,10");
        List<Transaction> actual = parser.parse(rawData);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseString_notOk() {
        rawData.add("");
        parser.parse(rawData);
    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidData_notOk() {
        rawData.add("m,pineapple,30");
        parser.parse(rawData);
    }

    @After
    public void clearList() {
        rawData.clear();
    }
}

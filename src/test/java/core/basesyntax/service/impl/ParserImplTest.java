package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParserImplTest {
    private static final Parser parser = new ParserImpl();
    private static final List<String> rawData = new ArrayList<>();

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
    public void parseInvalidValue_notOk() {
        rawData.add("m,pineapple,30");
        parser.parse(rawData);
    }

    @After
    public void clearList() {
        rawData.clear();
    }
}

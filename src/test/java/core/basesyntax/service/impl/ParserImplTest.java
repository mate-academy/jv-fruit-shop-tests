package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static List<String> data;
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
        data = new ArrayList<>();
    }

    @Before
    public void setUp() {
        data.add("type,fruit,quantity");
    }

    @Test(expected = RuntimeException.class)
    public void emptyString_notOk() {
        data.add("");
        parser.parseData(data);
    }

    @Test
    public void validData_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("apple", 30,
                FruitTransaction.Operation.BALANCE));
        data.add("b,apple,30");
        List<FruitTransaction> actual = parser.parseData(data);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void invalidData_notOk() {
        data.add("k,orange,30");
        parser.parseData(data);
    }

    @After
    public void tearDown() throws Exception {
        data.clear();
    }
}

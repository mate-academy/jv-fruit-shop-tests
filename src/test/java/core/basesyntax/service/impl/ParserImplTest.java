package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static List<String> testList;
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        testList = new ArrayList<>();
        parser = new ParserImpl();
    }

    @Before
    public void setUp() {
        testList.add("type,fruit,quantity");
    }

    @After
    public void tearDown() {
        testList.clear();
    }

    @Test(expected = RuntimeException.class)
    public void parseEmptyString_notOk() {
        testList.add("");
        parser.parseData(testList);
    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidData_notOk() {
        testList.add("m,banana,10");
        parser.parseData(testList);
    }

    @Test
    public void parseValidData_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 10));
        testList.add("s,orange,10");
        List<FruitTransaction> actual = parser.parseData(testList);
        assertEquals(expected, actual);
    }
}

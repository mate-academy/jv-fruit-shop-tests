package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitShopTransactions;
import core.basesyntax.service.FruitParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserImplTest {
    private static List<String> testList;
    private static FruitParser parser;

    @BeforeClass
    public static void beforeClass() {
        testList = new ArrayList<>();
        parser = new FruitParserImpl();
    }

    @Before
    public void setUp() {
        testList.add("type,fruit,quantity");
    }

    @Test(expected = RuntimeException.class)
    public void parseEmptyString_notOk() {
        testList.add("");
        parser.parse(testList);
    }

    @Test(expected = RuntimeException.class)
    public void parseInvalidData_notOk() {
        testList.add("t,banana,10");
        parser.parse(testList);
    }

    @Test
    public void parseValidData_ok() {
        List<FruitShopTransactions> expected = new ArrayList<>();
        expected.add(new FruitShopTransactions(
                FruitShopTransactions.Operation.BALANCE,
                "apple",
                100));
        testList.add("b,apple,100");
        List<FruitShopTransactions> actual = parser.parse(testList);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        testList.clear();
    }
}

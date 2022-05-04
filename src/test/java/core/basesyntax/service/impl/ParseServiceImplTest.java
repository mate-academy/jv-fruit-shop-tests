package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParseService parseService;

    @BeforeClass
    public static void beforeClass() {
        parseService = new ParseServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parse_UnknownOperation_NotOk() {
        List<String> list = new ArrayList<>();
        list.add("b-apple-50");
        list.add("r-apple-20");
        list.add("u-apple-50");
        parseService.getInfo(list);
    }

    @Test
    public void parse_NullValue_NotOk() {
        List<String> list = new ArrayList<>();
        list.add("b-apple-50");
        list.add(null);
        list.add(null);
        list.add("r-apple-20");
        List<FruitTransaction> actual = parseService.getInfo(list);
        int expected = 2;
        assertEquals("Size not equals expected size!", actual.size(), expected);
    }

    @Test
    public void parse_CorrectValue_Ok() {
        List<String> list = new ArrayList<>();
        list.add("b-apple-50");
        list.add("p-apple-20");
        list.add("s-apple-50");
        list.add("r-apple-52");
        List<FruitTransaction> actual = parseService.getInfo(list);
        int expected = 4;
        assertEquals(actual.size(), expected);
    }
}

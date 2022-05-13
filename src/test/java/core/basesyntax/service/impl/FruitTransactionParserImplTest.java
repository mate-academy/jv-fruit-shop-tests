package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parse_unknownOperation_NotOk() {
        List<String> list = new ArrayList<>();
        list.add("b,apple,50");
        list.add("r,apple,20");
        list.add("u,apple,50");
        fruitTransactionParser.parse(list);
    }

    @Test
    public void parse_nullValue_NotOk() {
        List<String> list = new ArrayList<>();
        list.add("b,orange,11");
        list.add(null);
        list.add(null);
        list.add("r,orange,15");
        List<FruitTransaction> actual = fruitTransactionParser.parse(list);
        int expected = 2;
        assertEquals("Size not equals expected size!", actual.size(), expected);
    }

    @Test
    public void parse_correctValue_Ok() {
        List<String> list = new ArrayList<>();
        list.add("b,apple,21");
        list.add("p,apple,31");
        list.add("s,apple,33");
        list.add("r,apple,22");
        List<FruitTransaction> actual = fruitTransactionParser.parse(list);
        int expected = 4;
        assertEquals(actual.size(), expected);
    }
}

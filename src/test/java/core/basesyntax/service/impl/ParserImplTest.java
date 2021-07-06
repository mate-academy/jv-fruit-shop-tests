package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitDto;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void test_Parser_Ok() {
        List<String> strings = new ArrayList<>();
        strings.add("type,fruit,quantity");
        strings.add("s,banana,150");
        FruitDto expected = new FruitDto("s", "banana", 150);
        FruitDto actual = parser.parseLines(strings).get(0);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void test_ParserCount_NotOk() {
        List<String> strings = new ArrayList<>();
        strings.add("type,fruit,quantity");
        strings.add("s,banana,-15");
        parser.parseLines(strings);
    }

    @Test(expected = RuntimeException.class)
    public void test_ParserLine_NotOk() {
        List<String> strings = new ArrayList<>();
        strings.add("type,fruit,quantity");
        strings.add("sadadd");
        parser.parseLines(strings);
    }
}

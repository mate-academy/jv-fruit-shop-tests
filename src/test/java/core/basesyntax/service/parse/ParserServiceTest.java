package core.basesyntax.service.parse;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceTest {
    private static ParserService parserService;

    @BeforeClass
    public static void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parse_validCase_OK() {
        List<String> input = new ArrayList<>();
        input.add("operation,fruit,quantity");
        input.add("b,apple,18");
        input.add("b,banana,25");
        input.add("p,banana,6");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b",
                "apple", 18));
        expected.add(new FruitTransaction("b",
                "banana", 25));
        expected.add(new FruitTransaction("p",
                "banana", 6));
        List<FruitTransaction> actual = parserService.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_header_Ok() {
        List<String> input = new ArrayList<>();
        input.add("operation,fruit,quantity");
        List<FruitTransaction> expected = Collections.EMPTY_LIST;
        List<FruitTransaction> actual = parserService.parse(input);
        assertEquals(expected, actual);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void parse_EmptyInput_NotOk() {
        parserService.parse(new ArrayList<>());
    }

    @Test (expected = NullPointerException.class)
    public void parse_NullInput_NotOk() {
        parserService.parse(null);
    }
}


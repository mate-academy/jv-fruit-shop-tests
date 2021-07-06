package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dto.FruitDto;
import core.basesyntax.service.validator.ValidatorImpl;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl(new ValidatorImpl());
    }

    @Test
    public void test_Parse_Ok() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("type,fruit,quantity");
        strings.add("p,apple,20");
        FruitDto expected = new FruitDto("p", "apple", 20);
        FruitDto actual = parser.parseToDto(strings).get(0);
        assertEquals(expected, actual);
    }
}

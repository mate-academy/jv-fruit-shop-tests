package core.basesyntax.service;

import core.basesyntax.dto.FruitDto;
import core.basesyntax.service.impl.ParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void parse_stringListToFruitDto_OK() {
        List<FruitDto> actual = parser.parseAndValidateLines(
                List.of("type,fruit,quantity", "b,banana,20", "b,apple,100"));
        List<FruitDto> expected = new ArrayList<>();
        expected.add(new FruitDto("b", "banana", 20));
        expected.add(new FruitDto("b", "apple", 100));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parse_stringListWithInvalidData_Ok() {
        List<FruitDto> actual = parser.parseAndValidateLines(
                List.of("type,fruit,quantity", "w,banana,20",
                "b,grape,-100", "s,banana,100", "p,banana,13"));
        List<FruitDto> expected = new ArrayList<>();
        expected.add(new FruitDto("s", "banana", 100));
        expected.add(new FruitDto("p", "banana", 13));
        Assert.assertEquals(expected, actual);
    }
}

package core.basesyntax.fruit.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitDtoParserImplTest {
    private static final FruitDtoParser FRUIT_DTO_PARSER = new FruitDtoParserImpl();

    @Test
    public void parseFruitDto_validLine_ok() {
        FruitDto actual = FRUIT_DTO_PARSER.parseFruitDto("n,apple,23");
        FruitDto expected = new FruitDto("apple", 23);
        assertEquals(expected, actual);
    }

    @Test
    public void parseFruitDto_customActivity_ok() {
        FruitDto actual = FRUIT_DTO_PARSER.parseFruitDto("12f,tangerine,12");
        FruitDto expected = new FruitDto("tangerine", 12);
        assertEquals(expected, actual);
    }

    @Test
    public void parseFruitDto_customFruit_ok() {
        FruitDto actual = FRUIT_DTO_PARSER.parseFruitDto("r,a2@tr2g,43");
        FruitDto expected = new FruitDto("a2@tr2g", 43);
        assertEquals(expected, actual);
    }

    @Test
    public void parseFruitDto_fruitWithWhitespaces_ok() {
        FruitDto actual = FRUIT_DTO_PARSER.parseFruitDto("b, orange  ,43");
        FruitDto expected = new FruitDto("orange", 43);
        assertEquals(expected, actual);
    }

    @Test
    public void parseFruitDto_amountWithWhiteSpaces_ok() {
        FruitDto actual = FRUIT_DTO_PARSER.parseFruitDto("b,apple,  20 ");
        FruitDto expected = new FruitDto("apple", 20);
        assertEquals(expected, actual);
    }
}

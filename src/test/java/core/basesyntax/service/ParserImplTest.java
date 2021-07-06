package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.dto.FruitDto;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {

    private static Validator validator;
    private static Parser parser;

    @BeforeClass
    public static void setValidatorParser() {
        validator = new ValidatorCsv();
        parser = new ParserImpl(validator);
    }

    @Test
    public void parser_parseString_ok() {
        FruitDto expected = new FruitDto("r", "banane", 100);
        FruitDto actual = parser.parseToFruitDto("r,banane,100");
        assertEquals(expected, actual);
    }

    @Test
    public void parser_parseString_notOk() {
        FruitDto expected = new FruitDto("y", "random", 200);
        FruitDto actual = parser.parseToFruitDto("r,banane,100");
        assertNotEquals(expected, actual);
    }
}

package core.basesyntax.service.parsers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.parsers.impl.FruitParserImpl;
import org.junit.Before;
import org.junit.Test;

public class FruitParserTest {
    private static FruitParser fruitParser;

    @Before
    public void setUp() {
        fruitParser = new FruitParserImpl();
    }

    @Test
    public void parse_getFruitWithFruitParser_OK() {
        assertEquals(fruitParser.parse("banana"), Fruit.BANANA);
    }

    @Test(expected = RuntimeException.class)
    public void parse_getFruitWithNullValue_OK() {
        assertEquals(fruitParser.parse(null), Fruit.BANANA);
    }

    @Test(expected = RuntimeException.class)
    public void parse_getFruitWithWrongFruitName_OK() {
        System.out.println(fruitParser.parse("grape"));
    }
}

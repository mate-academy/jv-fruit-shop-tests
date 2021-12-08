package core.basesyntax.parsers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.parsers.impl.FruitParserImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserTest {
    private static FruitParser fruitParser;

    @BeforeClass
    public static void setUp() {
        fruitParser = new FruitParserImpl();
    }

    @Test
    public void parse_getFruitWithFruitParser_OK() {
        assertEquals(Fruit.BANANA, fruitParser.parse("banana"));
        assertEquals(Fruit.APPLE, fruitParser.parse("apple"));
    }
}

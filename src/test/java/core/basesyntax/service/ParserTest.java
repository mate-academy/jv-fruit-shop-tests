package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static Parser<FruitTransaction> parser;
    private static List<String> data;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void parse_validOutput_Ok() {
        data = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"), 20));
        expected.add(new FruitTransaction("b", new Fruit("apple"), 100));
        List<FruitTransaction> actual = parser.parse(data);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void parse_nullData_NotOk() {
        parser.parse(null);
    }
}

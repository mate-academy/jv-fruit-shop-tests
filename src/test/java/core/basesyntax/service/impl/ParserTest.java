package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.function.Function;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    private static Function<List<String>, List<FruitTransaction>> parser;

    @BeforeClass
    public static void setUpFirst() {
        parser = new Parser();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void apply_wrongData_NotOk() {
        parser.apply(List.of("b,apple"));
    }

    @Test
    public void apply_correctData_Ok() {
        List<FruitTransaction> expected = List.of(new FruitTransaction(new Fruit("apple"), 5,"b"));
        List<FruitTransaction> actual = parser.apply(List.of("b,apple,5"));
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullData_NotOk() {
        parser.apply(null);
    }
}

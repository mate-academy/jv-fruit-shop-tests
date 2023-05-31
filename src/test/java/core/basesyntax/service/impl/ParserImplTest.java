package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserImplTest {
    private List<Transaction> actual;

    @Before
    public void setUp() {
        List<String> lines = List.of("type,fruit,quantity",
                "b,banana,20",
                "p,lemon,10",
                "s,berry,15");
        actual = new ParserImpl().parse(lines);
    }

    @Test
    public void parseTransaction_Ok() {
        List<Transaction> expected = List.of(new Transaction("b", new Fruit("banana"), 20),
                new Transaction("p", new Fruit("lemon"), 10),
                new Transaction("s", new Fruit("berry"), 15));
        Assert.assertEquals(expected, actual);
    }
}

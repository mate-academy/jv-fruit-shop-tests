package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserServiceImplTest {
    private ParserServiceImpl parserService;

    @Before
    public void setUp() {
        parserService = new ParserServiceImpl();
    }

    @Test
    public void parseStringOk() {
        List<FruitTransaction> expected = Collections.singletonList(new FruitTransaction("b",
                new Fruit("banana"), 88));
        List<String> actualString = new ArrayList<>();
        actualString.add("type,fruit,quantity");
        actualString.add("b,banana,88");
        List<FruitTransaction> actual = parserService.parse(actualString);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void parseNullNotOk() {
        parserService.parse(null);
    }
}

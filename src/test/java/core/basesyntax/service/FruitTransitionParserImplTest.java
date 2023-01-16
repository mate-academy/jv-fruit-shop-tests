package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitTransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransitionParserImplTest {
    private static List<String> data;
    private static FruitTransactionParser transitionParser;

    @BeforeClass
    public static void setUp() {
        transitionParser = new FruitTransactionParserImpl();
        data = new ArrayList<>();
    }

    @Before
    public void beforeAll() {
        data.clear();
        data.add("type,fruit,quantity");
    }

    @Test
    public void parseTransition_getFruit_ok() {
        data.add("b,apple,100");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("apple"), 100));
        List<FruitTransaction> actual = transitionParser.parse(data);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void parseTransition_singleElement_ok() {
        data.add("b,apple,100");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("apple"), 100));
        List<FruitTransaction> actual = transitionParser.parse(data);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void parseTransition_threeElement_ok() {
        data.add("b,apple,100");
        data.add("b,apple,150");
        data.add("s,banana,200");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("apple"), 100));
        expected.add(new FruitTransaction("b", new Fruit("apple"), 150));
        expected.add(new FruitTransaction("s", new Fruit("banana"), 200));
        List<FruitTransaction> actual = transitionParser.parse(data);
        assertEquals(expected.toString(), actual.toString());
    }
}

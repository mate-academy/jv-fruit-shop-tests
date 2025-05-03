package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransition;
import core.basesyntax.service.impl.OperationValidatorImpl;
import core.basesyntax.service.impl.TransitionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransitionParserImplTest {
    private static List<String> data;
    private static TransitionParser transitionParser;

    @BeforeClass
    public static void setUp() {
        OperationValidator validator = new OperationValidatorImpl();
        transitionParser = new TransitionParserImpl(validator);
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
        List<FruitTransition> expected = new ArrayList<>();
        expected.add(new FruitTransition("b", new Fruit("apple"), 100));
        List<FruitTransition> actual = transitionParser.parseTransition(data);
        assertEquals(expected, actual);
    }

    @Test
    public void parseTransition_singleElement_ok() {
        data.add("b,apple,100");
        List<FruitTransition> expected = new ArrayList<>();
        expected.add(new FruitTransition("b", new Fruit("apple"), 100));
        List<FruitTransition> actual = transitionParser.parseTransition(data);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void parseTransition_threeElement_ok() {
        data.add("b,apple,100");
        data.add("b,apple,150");
        data.add("s,banana,200");
        List<FruitTransition> expected = new ArrayList<>();
        expected.add(new FruitTransition("b", new Fruit("apple"), 100));
        expected.add(new FruitTransition("b", new Fruit("apple"), 150));
        expected.add(new FruitTransition("s", new Fruit("banana"), 200));
        List<FruitTransition> actual = transitionParser.parseTransition(data);
        assertEquals(expected.toString(), actual.toString());
    }
}

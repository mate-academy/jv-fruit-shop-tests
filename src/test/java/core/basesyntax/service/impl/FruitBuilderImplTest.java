package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitBuilder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitBuilderImplTest {
    private static FruitBuilder fruitBuilder;

    @BeforeClass
    public static void setUp() {
        fruitBuilder = new FruitBuilderImpl();
    }

    @Test
    public void fruitBuilder_validDate_ok() {
        List<String> sourceDate = new ArrayList<>();
        sourceDate.add("b,banana,20");
        sourceDate.add("b,apple,100");
        sourceDate.add("s,banana,100");
        sourceDate.add("p,banana,13");
        sourceDate.add("r,apple,10");
        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("b", "banana", 20));
        expected.add(new Fruit("b", "apple", 100));
        expected.add(new Fruit("s", "banana", 100));
        expected.add(new Fruit("p", "banana", 13));
        expected.add(new Fruit("r", "apple", 10));
        List<Fruit> actual = fruitBuilder.buildFruit(sourceDate);
        Assert.assertEquals(expected, actual);
    }
}

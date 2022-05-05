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
    public void fruitBuildIsOk() {
        List<String> sourceData = new ArrayList<>();
        sourceData.add("type,fruit,quantity");
        sourceData.add("b,banana,20");
        sourceData.add("s,apple,100");
        sourceData.add("p,papaya,100");

        List<Fruit> expected = new ArrayList<>();
        expected.add(new Fruit("b", "banana", 20));
        expected.add(new Fruit("s", "apple", 100));
        expected.add(new Fruit("p", "papaya", 100));

        List<Fruit> actual = fruitBuilder.buildFruit(sourceData);
        Assert.assertEquals(expected, actual);
    }
}

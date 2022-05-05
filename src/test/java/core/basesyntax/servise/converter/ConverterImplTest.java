package core.basesyntax.servise.converter;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConverterImplTest {
    private Fruit fruit;
    private Converter converter;

    @Before
    public void setUp() {
        fruit = new Fruit("apple");
        converter = new ConverterImpl();
    }

    @Test
    public void convert_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", fruit, 20));
        expected.add(new FruitTransaction("p", fruit, 10));
        expected.add(new FruitTransaction("r", fruit, 5));
        expected.add(new FruitTransaction("s", new Fruit("banana"), 7));
        List<String> sourceList = List.of("operation,name,quantity",
                "b,apple,20",
                "p,apple,10",
                "r,apple,5",
                "s,banana,7");
        List<FruitTransaction> actual = converter.convert(sourceList);
        Assert.assertEquals(expected, actual);
    }
}

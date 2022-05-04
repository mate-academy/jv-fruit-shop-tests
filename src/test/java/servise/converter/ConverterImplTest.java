package servise.converter;

import java.util.ArrayList;
import java.util.List;
import model.Fruit;
import model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConverterImplTest {
    private final Fruit fruit;
    private final Converter converter;

    private ConverterImplTest() {
        this.fruit = new Fruit("apple");
        this.converter = new ConverterImpl();
    }

    @Test
    void convert_ok() {
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
        Assertions.assertEquals(expected, actual);
    }
}

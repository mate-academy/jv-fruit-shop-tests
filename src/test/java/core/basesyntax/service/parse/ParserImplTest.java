package core.basesyntax.service.parse;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void parse_ValidInputData_Ok() {
        List<String> data = new ArrayList<>();;
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("b,apple,100");

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction("b", new Fruit("banana"),
                20));
        expected.add(new FruitTransaction("b", new Fruit("apple"),
                100));
        List<FruitTransaction> actual = parser.parse(data);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parse_ValidInputOneLine_Ok() {
        List<String> data = new ArrayList<>();;
        data.add("type,fruit,quantity");
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = parser.parse(data);
        Assert.assertEquals(expected, actual);
    }
}

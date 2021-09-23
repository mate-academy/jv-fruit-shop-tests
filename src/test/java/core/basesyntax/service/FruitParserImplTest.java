package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserImplTest {
    private static List<String> data;
    private static FruitParserImpl fruitParser;

    @BeforeClass
    public static void beforeAll() {
        fruitParser = new FruitParserImpl();
        data = new ArrayList<>();
    }

    @Test
    public void parseData_theCorrectData_ok() {
        List<String> data = new ArrayList<>();
        data.add("b,banana,10");
        data.add("b,apple,15");
        List<FruitRecordDto> expected = new ArrayList<>();
        expected.add(new FruitRecordDto("b", new Fruit("banana"), 10));
        expected.add(new FruitRecordDto("b", new Fruit("apple"), 15));
        List<FruitRecordDto> actual = fruitParser.apply(data);
        Assert.assertEquals("Data was not parsed correctly!", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseData_incorrectData_notOk() {
        data.add("b,banana,-10");
        data.add("r,apple,");
        fruitParser.apply(data);
    }

    @After
    public void tearDown() {
        data.clear();
    }
}

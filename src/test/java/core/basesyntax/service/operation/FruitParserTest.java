package core.basesyntax.service.operation;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecord;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserTest {
    private static List<String> lineList;
    private static List<FruitRecord> expected;
    private static List<FruitRecord> actual;
    private static FruitParser fruitParser;

    @BeforeClass
    public static void beforeClass() {
        actual = new ArrayList<>();
        fruitParser = new FruitParser();
        lineList = new ArrayList<>();
        lineList.add("b,banana,100");
        lineList.add("r,apple,200");
        lineList.add("s,banana,4");
        expected = new ArrayList<>();
        expected.add(new FruitRecord("b", new Fruit("banana"), 100));
        expected.add(new FruitRecord("r", new Fruit("apple"), 200));
        expected.add(new FruitRecord("s", new Fruit("banana"), 4));
    }

    @Test
    public void createDto_validData_ok() {
        actual = fruitParser.createDto(lineList);
        Assert.assertEquals(expected, actual);
    }
}

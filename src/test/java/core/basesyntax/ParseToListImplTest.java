package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.Operation;
import core.basesyntax.service.ParseToList;
import core.basesyntax.service.Parser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseToListImplTest {
    private static final List<String> listOfStringsWithOneLine = new ArrayList<>();
    private static final List<String> listOfStringsWithThreeLines = new ArrayList<>();
    private static List<String> nullList;
    private static final ParseToList parseToList = new Parser();

    @BeforeClass
    public static void setUp() {
        listOfStringsWithOneLine.add("b,banana,150");
        listOfStringsWithThreeLines.add("b,banana,20");
        listOfStringsWithThreeLines.add("b,apple,100");
        listOfStringsWithThreeLines.add("s,orange,60");
    }

    @Test
    public void parseOneLine_Ok() {
        List<FruitRecordDto> expected = new ArrayList<>();
        expected.add(new FruitRecordDto(Operation.BALANCE, new Fruit("banana"), 150));
        List<FruitRecordDto> actual = parseToList.parseToDto(listOfStringsWithOneLine);
        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseThreeLines_Ok() {
        List<FruitRecordDto> expected = new ArrayList<>();
        expected.add(new FruitRecordDto(Operation.BALANCE, new Fruit("banana"), 20));
        expected.add(new FruitRecordDto(Operation.BALANCE, new Fruit("apple"), 100));
        expected.add(new FruitRecordDto(Operation.SUPPLY, new Fruit("orange"), 60));
        List<FruitRecordDto> actual = parseToList.parseToDto(listOfStringsWithThreeLines);
        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void noDataInFile() {
        parseToList.parseToDto(nullList);
    }
}

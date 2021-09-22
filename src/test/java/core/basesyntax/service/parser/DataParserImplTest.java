package core.basesyntax.service.parser;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitRecordDto;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataParserImplTest {
    private static DataParser dataParser;
    private static List<String> data;
    private static List<FruitRecordDto> expected;

    @BeforeClass
    public static void beforeClass() {
        dataParser = new DataParserImpl();
        data = new ArrayList<>();
        expected = new ArrayList<>();
    }

    @Test
    public void parseData_correctData_Ok() {
        data.add("title");
        data.add("r,apple,13");
        data.add("s,banana,57");
        data.add("b,apple,12");
        expected.add(new FruitRecordDto(new Fruit("apple"),
                 13, FruitRecordDto.OperationType.RETURN));
        expected.add(new FruitRecordDto(new Fruit("banana"),
                57, FruitRecordDto.OperationType.SUPPLY));
        expected.add(new FruitRecordDto(new Fruit("apple"),
                12, FruitRecordDto.OperationType.BALANCE));
        List<FruitRecordDto> actual = dataParser.parseData(data);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseData_incorrectData_NotOk() {
        data.add("title");
        data.add(",apple,13");
        data.add("s,banana,-57");
        dataParser.parseData(data);
    }
}

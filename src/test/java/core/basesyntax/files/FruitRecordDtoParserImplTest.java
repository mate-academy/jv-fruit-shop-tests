package core.basesyntax.files;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.Operation;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FruitRecordDtoParserImplTest {
    private static FruitRecordDtoParser fruitRecordDtoParser;
    private static List<String> fruitsList;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitRecordDtoParser = new FruitRecordDtoParserImpl();
        fruitsList = new ArrayList<>();
    }

    @Test
    public void parseStrings_isOk() {
        fruitsList.add("type,fruit,quantity");
        fruitsList.add("b,banana,10");
        fruitsList.add("p,banana,5");
        fruitsList.add("r,banana,5");
        fruitsList.add("s,banana,10");
        List<FruitRecordDto> actual = fruitRecordDtoParser.parseStrings(fruitsList);
        List<FruitRecordDto> expected = new ArrayList<>();
        expected.add(new FruitRecordDto(Operation.BALANCE, "banana", 10));
        expected.add(new FruitRecordDto(Operation.PURCHASE, "banana", 5));
        expected.add(new FruitRecordDto(Operation.RETURN, "banana", 5));
        expected.add(new FruitRecordDto(Operation.SUPPLY, "banana", 10));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseStrings_notOk() {
        fruitsList.add("r,-5");
        fruitsList.add("/345");
        new FruitRecordDtoParserImpl().parseStrings(fruitsList);
    }
}
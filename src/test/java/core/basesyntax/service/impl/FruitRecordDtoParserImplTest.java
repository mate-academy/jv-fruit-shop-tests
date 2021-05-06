package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitRecordDtoParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static FruitRecordDtoParser parser;
    private static List<String> corectdatalist;
    private static List<FruitRecordDto> expected;
    private static List<FruitRecordDto> actual;

    @BeforeClass
    public static void beforeClass() {
        parser = new FruitRecordDtoParserImpl();
        corectdatalist = List.of("b,apple,23");
    }

    @Test
    public void getDataFromListIfDataCorrect_Ok() {
        expected = List.of(new FruitRecordDto("b","apple",23));
        actual = parser.parse(corectdatalist);
        FruitRecordDto element1 = expected.get(0);
        FruitRecordDto element2 = actual.get(0);
        String result = element1.getOperationType()
                + element1.getFruitName()
                + element1.getQuantity();
        String result2 = element2.getOperationType()
                + element2.getFruitName()
                + element2.getQuantity();
        assertEquals(result, result2);
    }

    @Test(expected = RuntimeException.class)
    public void getDataWithIncorrectLength_NotOk() {
        parser.parse(List.of("1,2"));
    }

    @Test(expected = RuntimeException.class)
    public void getDataWithWrongOperationType_NotOk() {
        parser.parse(List.of("bp,apple,23"));
    }

    @Test
    public void getDataWithFirstFileLine_Ok() {
        List<String> list = List.of("type,fruit,quantity","b,apple,23");
        expected = List.of(new FruitRecordDto("b","apple",23));
        actual = parser.parse(list);
        FruitRecordDto element1 = expected.get(0);
        FruitRecordDto element2 = actual.get(0);
        String result = element1.getOperationType()
                + element1.getFruitName()
                + element1.getQuantity();
        String result2 = element2.getOperationType()
                + element2.getFruitName()
                + element2.getQuantity();
        assertEquals(result, result2);
    }

    @Test(expected = RuntimeException.class)
    public void getDataWithEmptyList_NotOk() {
        List<String> list = new ArrayList<>();
        parser.parse(list);
    }
}

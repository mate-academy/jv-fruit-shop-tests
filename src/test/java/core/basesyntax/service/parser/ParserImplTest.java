package core.basesyntax.service.parser;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitOperationDto;
import core.basesyntax.model.OperationType;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static Parser<List<String>, List<FruitOperationDto>> parser;
    private static List<String> validList;
    private static List<String> invalidList;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
        validList = new ArrayList<>();
        validList.add("b,banana,20");
        validList.add("r,apple,100");
        invalidList = new ArrayList<>();
        invalidList.add("***");
    }

    @Test
    public void parse_ValidList_Ok() {
        List<FruitOperationDto> expected = new ArrayList<>();
        expected.add(new FruitOperationDto(OperationType.BALANCE,
                new Fruit("banana"), 20));
        expected.add(new FruitOperationDto(OperationType.RETURN,
                new Fruit("apple"), 100));
        List<FruitOperationDto> actual = parser.parse(validList);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_InvalidList_NotOk() {
        parser.parse(invalidList);
    }

    @Test
    public void parse_EmptyList_Ok() {
        List<FruitOperationDto> actual = parser.parse(new ArrayList<>());
        List<FruitOperationDto> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

}

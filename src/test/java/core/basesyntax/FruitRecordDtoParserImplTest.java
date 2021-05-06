package core.basesyntax;

import core.basesyntax.model.OperationType;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.service.FruitRecordDtoParser;
import core.basesyntax.service.impl.FruitRecordDtoParserImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitRecordDtoParserImplTest {
    private static final List<String> EMPTY = new ArrayList<>();
    private static final List<String> INVALID = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,&3",
            "r,apple,10",
            "p,apple,13");
    private static final List<String> VALID = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10");

    private static FruitRecordDtoParser fruitRecordDtoParser;

    @BeforeClass
    public static void initialization() {
        fruitRecordDtoParser = new FruitRecordDtoParserImpl();
    }

    @Test
    public void parseLines_validList_ok() {
        List<FruitRecordDto> expected = new ArrayList<>();
        expected.add(new FruitRecordDto(OperationType.BALANCE, "banana", 20));
        expected.add(new FruitRecordDto(OperationType.BALANCE, "apple", 100));
        expected.add(new FruitRecordDto(OperationType.SUPPLY, "banana", 100));
        expected.add(new FruitRecordDto(OperationType.PURCHASE, "banana", 13));
        expected.add(new FruitRecordDto(OperationType.RETURN, "apple", 10));
        List<FruitRecordDto> actual = fruitRecordDtoParser.parse(VALID);
        Assert.assertEquals(expected.toString(), actual.toString());
    }

    @Test (expected = RuntimeException.class)
    public void parse_incorrectQuantityNotNumber_notOk() {
        List<String> lines = new ArrayList(Arrays.asList("type,fruit,quantity",
                "b,banana,one"));
        fruitRecordDtoParser.parse(lines);
    }

    @Test(expected = RuntimeException.class)
    public void parseLines_invalidList_notOk() {
        fruitRecordDtoParser.parse(INVALID);
    }

    @Test(expected = RuntimeException.class)
    public void parseLines_emptyList_notOk() {
        fruitRecordDtoParser.parse(EMPTY);
    }
}
